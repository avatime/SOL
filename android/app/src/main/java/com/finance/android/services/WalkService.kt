package com.finance.android.services

import android.app.*
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.finance.android.MainActivity
import com.finance.android.R
import com.finance.android.datastore.WalkStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.text.DecimalFormat

class WalkService : Service(), SensorEventListener {
    companion object {
        const val CHANNEL_ID = "만보기"
        const val CHANNEL_NAME = "만보기"
        const val NOTIFICATION_ID = 1
    }

    private lateinit var notificationManager: NotificationManager
    private lateinit var sensorManager: SensorManager
    private var steps = -1
    private val job = SupervisorJob()

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.i("TEST", "WalkService - onCreate")
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER),
            SensorManager.SENSOR_DELAY_GAME
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("TEST", "WalkService - onDestroy")

        notificationManager.cancel(NOTIFICATION_ID)
        sensorManager.unregisterListener(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("TEST", "WalkService - onStartCommand")
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, getNotification())
        return START_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_NONE
            ).apply {
                enableVibration(true)
                setSound(null, null)
                importance = NotificationManager.IMPORTANCE_NONE
                setShowBadge(false)
            }
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(serviceChannel)
        }
    }

    private fun getNotification(): Notification {
        val context = this
        CoroutineScope(Dispatchers.IO + job).launch {
            WalkStore(context).setActive(steps != -1)
        }

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            if (Build.VERSION_CODES.M <= Build.VERSION.SDK_INT) {
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            } else {
                PendingIntent.FLAG_UPDATE_CURRENT
            }
        )

        val view = RemoteViews(context.packageName, R.layout.notification_walk_count).apply {
            setTextViewText(R.id.walkCount, DecimalFormat("#,###").format(steps))
        }
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(view)
            .setShowWhen(false)
//            .setContentTitle("SOL# 만보기")
//            .setContentText(if (steps == -1) "신체활동 권한을 켜주세요" else "$steps 걸음")
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        Log.i("TEST", "WalkService - onSensorChanged ${p0?.values?.get(0)?.toInt() ?: -1}")
        steps = p0?.values?.get(0)?.toInt() ?: -1
        if (p0 == null) {
            steps = -1
            notificationManager.notify(NOTIFICATION_ID, getNotification())
            return
        }

        val context = this
        CoroutineScope(Dispatchers.IO + job).launch {
            WalkStore(context).getCount().collect {
                steps = it + 1
                WalkStore(context).setCount(steps)
                notificationManager.notify(NOTIFICATION_ID, getNotification())
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }
}

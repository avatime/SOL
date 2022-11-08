//package com.finance.android.walk
//
//import android.app.NotificationChannel
//import android.app.NotificationManager
//import android.content.Context
//import android.os.Build
//import androidx.core.app.NotificationCompat
//import androidx.work.Data
//import androidx.work.Worker
//import androidx.work.WorkerParameters
//import com.finance.android.R
//
//class NotificationWorker(context: Context, workerParameters: WorkerParameters):
//    Worker(context, workerParameters) {
//    override fun doWork(): Result {
//        val taskData = inputData
//        val taskDataString = taskData.getString("message_status")
//
//        showNotification(
//            "Make it Easy",
//            taskDataString.toString()
//        )
//
//        val outputData = Data.Builder().putString(WORK_RESULT, "Task Finished").build()
//
//        return Result.success(outputData)
//    }
//
//    private fun showNotification(task: String, desc: String) {
//        val manager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE)
//                as NotificationManager
//        val channelId = "message_channel"
//        val channelName = "message_name"
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(channelId, channelName,
//                NotificationManager.IMPORTANCE_DEFAULT)
//            manager.createNotificationChannel(channel)
//        }
//
//        val builder = NotificationCompat.Builder(applicationContext, channelId)
//            .setContentTitle(task)
//            .setContentText(desc)
//            .setSmallIcon(R.drawable.ic_launcher_foreground)
//
//        manager.notify(1, builder.build())
//    }
//
//    companion object {
//        const val WORK_RESULT = "work_result"
//    }
//}
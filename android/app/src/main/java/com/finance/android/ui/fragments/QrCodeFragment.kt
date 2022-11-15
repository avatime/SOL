package com.finance.android.ui.fragments

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.finance.android.R
import com.finance.android.ui.components.BackHeaderBar
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.journeyapps.barcodescanner.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QRCodeFragment(
    onClose: () -> Unit = {}
) {
    var showQrCode by remember { mutableStateOf(false) }
    var generateQrCode by remember { mutableStateOf(false) }
    if(showQrCode) {
        QRScanner( onClose = {showQrCode = false} )
    }
    else if(generateQrCode) {
        MyQRCode( onClose = {generateQrCode = false} )
    }
    else {
        Scaffold(
            topBar = {
                BackHeaderBar(
                    text = "QR CODE",
                    onClickBack = onClose
                )
            }
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(top = it.calculateTopPadding())
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(dimensionResource(R.dimen.padding_medium))
            ) {
                Row() {
                    Button(onClick = { showQrCode = true }) {
                        Text(text = "QR 스캔", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
                    }
                    Button(onClick = { generateQrCode = true }) {
                        Text(text = "QR 생성", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun QRScanner(
    onClose: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            BackHeaderBar(
                text = "QR CODE",
                onClickBack = onClose
            )
        }
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(color = MaterialTheme.colorScheme.background)
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            AdminClubMembershipScanScreen()
            Text(text = "QR 스캐너", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
        }
    }
}

@Composable
fun AdminClubMembershipScanScreen() {
    val context = LocalContext.current
    var scanFlag by remember {
        mutableStateOf(false)
    }

    val compoundBarcodeView = remember {
        CompoundBarcodeView(context).apply {
            val capture = CaptureManager(context as Activity, this)
            capture.initializeFromIntent(context.intent, null)
            this.setStatusText("")
            capture.decode()
            this.decodeContinuous { result ->
                if(scanFlag){
                    return@decodeContinuous
                }
                scanFlag = true
                result.text?.let { qrCode->
                    //Do something and when you finish this something
                    println(qrCode)
                    //put scanFlag = false to scan another item
                    scanFlag = false
                }
                //If you don't put this scanFlag = false, it will never work again.
                //you can put a delay over 2 seconds and then scanFlag = false to prevent multiple scanning

            }
        }
    }

    AndroidView(
        modifier = Modifier,
        factory = { compoundBarcodeView },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun MyQRCode(
    onClose: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            BackHeaderBar(
                text = "QR CODE",
                onClickBack = onClose
            )
        }
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            val barcodeEncoder = BarcodeEncoder()
            val qrCodeWriter = QRCodeWriter()
            val bitmap = barcodeEncoder.encodeBitmap(
                "아바타임 가물가물 쏠# 까지 하면서 팀이름 하나 못 정한 우리팀 화이팅!!",
                BarcodeFormat.QR_CODE,
                1200,
                1200
            ).asImageBitmap()

            ForceBrightness()
            Image(modifier = Modifier.aspectRatio(1f).fillMaxWidth(), bitmap = bitmap, contentDescription = "qr 코드")
        }
    }
}

@Composable
fun ForceBrightness(brightness: Float = 1f) {
    val activity = requireNotNull(LocalContext.current.getActivity())
    DisposableEffect(Unit) {
        val attributes = activity.window.attributes
        val originalBrightness = attributes.screenBrightness
        activity.window.attributes = attributes.apply { screenBrightness = brightness }
        onDispose {
            activity.window.attributes = attributes.apply { screenBrightness = originalBrightness }
        }
    }
}

fun Context.getActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) {
            return context
        }
        context = context.baseContext
    }
    return null
}

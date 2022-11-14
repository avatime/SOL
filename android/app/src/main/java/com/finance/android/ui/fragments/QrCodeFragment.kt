package com.finance.android.ui.fragments

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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.finance.android.R
import com.finance.android.ui.components.BackHeaderBar
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.journeyapps.barcodescanner.BarcodeEncoder

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
            Text(text = "QR 스캐너", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
        }
    }
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
                .background(color = MaterialTheme.colorScheme.background)
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            val barcodeEncoder = BarcodeEncoder()
            val qrCodeWriter = QRCodeWriter()
            val bitmap = barcodeEncoder.encodeBitmap(
                "신한,11111111111",
                BarcodeFormat.QR_CODE,
                400,
                400
            ).asImageBitmap()

            Image(bitmap = bitmap, contentDescription = "qr 코드")
        }
    }
}

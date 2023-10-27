package com.example.googlescanner

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.googlescanner.ui.theme.GoogleScannerTheme
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val gmsScannerOptions = configureScannerOption()
        val instance = getBarcodeScannerInstance(gmsScannerOptions)
        setContent {
            GoogleScannerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScannerView.startActivity(this)
                    //initiateScanner(instance)
                }
            }
        }
    }

    private fun getBarcodeScannerInstance(gmsScannerOptions: GmsBarcodeScannerOptions): GmsBarcodeScanner {
        return GmsBarcodeScanning.getClient(this, gmsScannerOptions)
    }

    private fun configureScannerOption(): GmsBarcodeScannerOptions {
        return GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_QR_CODE,
                Barcode.FORMAT_AZTEC
            )
            .build()
    }

    private fun initiateScanner(gmsBarcodeScanner: GmsBarcodeScanner) {
        gmsBarcodeScanner.startScan()
            .addOnSuccessListener{barcode ->
                val result = barcode.rawValue
                val type = barcode.valueType
                val format = barcode.format
                Toast.makeText(this, "QR Code:${result}, Code Type:${type}, Code Format:${format}", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener{
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GoogleScannerTheme {
        Greeting("Android")
    }
}
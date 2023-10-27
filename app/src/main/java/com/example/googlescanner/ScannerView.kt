package com.example.googlescanner

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning


class ScannerView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gmsScannerOptions = configureScannerOption()
        val instance = getBarcodeScannerInstance(gmsScannerOptions)
        setContentView(R.layout.activity_scanner_view)
        var btnScann = findViewById<Button>(R.id.btnScan)
        btnScann.setOnClickListener {
            initiateScanner(instance)
        }
    }

    private fun initiateScanner(gmsBarcodeScanner: GmsBarcodeScanner) {
        gmsBarcodeScanner.startScan()
            .addOnSuccessListener{barcode ->
                val result = barcode.rawValue
                val type = barcode.displayValue
                val format = barcode.format
                val barcodeFormat: String = when (format) {
                    Barcode.FORMAT_QR_CODE -> "qr"
                    Barcode.FORMAT_CODE_128 -> "bar"
                    // Add more cases for other barcode formats as needed
                    else -> "Invalid Code Format"
                }
                Toast.makeText(this, "QR Code:${result},\n Code Format:${barcodeFormat}", Toast.LENGTH_LONG).show()

            }
            .addOnFailureListener{
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            }
    }

    private fun getBarcodeScannerInstance(gmsScannerOptions: GmsBarcodeScannerOptions): GmsBarcodeScanner {
        return GmsBarcodeScanning.getClient(this, gmsScannerOptions)
    }

    private fun configureScannerOption(): GmsBarcodeScannerOptions {
        return GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_ALL_FORMATS,
                Barcode.FORMAT_CODE_128
            )
            .build()
    }

    companion object{
        fun startActivity(context:Context){
            val intent = Intent(context, ScannerView::class.java)
            context.startActivity(intent)
        }
    }
}
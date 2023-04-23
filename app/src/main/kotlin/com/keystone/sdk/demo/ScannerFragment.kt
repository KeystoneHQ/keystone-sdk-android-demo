package com.keystone.sdk.demo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.zxing.integration.android.IntentIntegrator
import com.keystone.sdk.KeystoneSDK
import com.keystone.sdk.demo.databinding.FragmentScannerBinding

data class UR(
    val type: String,
    val cbor: String,
)

class ScannerFragment : Fragment() {

    private lateinit var binding: FragmentScannerBinding
    private lateinit var qrScanIntegrator: IntentIntegrator
    private var sdk: KeystoneSDK = KeystoneSDK()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScannerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupScanner()
        setOnClickListener()

        performAction()
    }

    private fun setupScanner() {
        qrScanIntegrator = IntentIntegrator.forSupportFragment(this)
        qrScanIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        qrScanIntegrator.setOrientationLocked(false)
    }

    private fun setOnClickListener() {
        binding.btnScan.setOnClickListener { performAction() }
    }

    private fun performAction() {
        sdk.resetQRDecoder()
        // Code to perform action when button is clicked.
        qrScanIntegrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result.contents == null) {
            return
        }

        // connect wallet (get account information)
        val decodedQR = sdk.decodeQR(result.contents)
        try {
            if (decodedQR == null) {
                qrScanIntegrator.initiateScan()
                return
            }
            val accounts = sdk.parseMultiAccounts(decodedQR)
            binding.scanResult.text = Gson().toJson(accounts)
        } catch (err: Exception) {
            if (decodedQR != null) {
                binding.scanResult.text = Gson().toJson(UR(decodedQR.type, decodedQR.cborBytes.toHexString()))
                println(decodedQR.cborBytes.toHexString())
            }
            Toast.makeText(binding.root.context, err.message, Toast.LENGTH_LONG).show()
        }
    }
}

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
import com.keystone.module.MultiAccounts
import com.keystone.sdk.KeystoneSDK
import com.keystone.sdk.demo.databinding.FragmentScannerBinding

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
        try {
            val decodedQR = sdk.decodeQR(result.contents)
            if (decodedQR == null) {
                qrScanIntegrator.initiateScan()
                return
            }
            val accounts: MultiAccounts = sdk.parseMultiAccounts(decodedQR.cbor)
            binding.scanResult.text = Gson().toJson(accounts)
        } catch (err: Exception) {
            Toast.makeText(binding.root.context, err.message, Toast.LENGTH_LONG).show()
        }
    }
}

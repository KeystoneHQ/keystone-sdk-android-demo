package com.keystone.sdk.demo

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.keystone.module.CosmosAccount
import com.keystone.module.CosmosSignRequest
import com.keystone.sdk.KeystoneCosmosSDK
import com.keystone.sdk.KeystoneEthereumSDK
import com.keystone.sdk.KeystoneSDK
import com.keystone.sdk.KeystoneSolanaSDK
import com.keystone.sdk.demo.databinding.FragmentPlayerBinding
import com.sparrowwallet.hummingbird.UREncoder
import java.util.Timer
import java.util.TimerTask

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PlayerFragment : Fragment() {

    private var _binding: FragmentPlayerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root

    }

    private fun displayQRCode(data: String): Bitmap {
        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 512, 512)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
            }
        }
        return bitmap
    }

    private fun genSolQRCode(): UREncoder {
        val requestId = "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d"
        val signData =
            "01000103c8d842a2f17fd7aab608ce2ea535a6e958dffa20caf669b347b911c4171965530f957620b228bae2b94c82ddd4c093983a67365555b737ec7ddc1117e61c72e0000000000000000000000000000000000000000000000000000000000000000010295cc2f1f39f3604718496ea00676d6a72ec66ad09d926e3ece34f565f18d201020200010c0200000000e1f50500000000"
        val path = "m/44'/501'/0'/0'"
        val xfp = "707EED6C"
        val address = ""
        val origin = "solflare"
        val signType = KeystoneSolanaSDK.SignType.Message
        val sdk = KeystoneSDK()
        KeystoneSDK.maxFragmentLen = 100
        return sdk.sol.generateSignRequest(requestId, signData, path, xfp, address, origin, signType)
    }

    private fun genEthQRCode(): UREncoder {
        val requestId = "6c3633c0-02c0-4313-9cd7-e25f4f296729"
        val signData = "6578616d706c652060706572736f6e616c5f7369676e60206d657373616765"
        val dataType = KeystoneEthereumSDK.DataType.PersonalMessage
        val path = "m/44'/60'/0'/0/0"
        val xfp = "707EED6C"
        val address = ""
        val origin = "eth"

        // tx to ur encoder
        val sdk = KeystoneSDK()
        KeystoneSDK.maxFragmentLen = 500
        return sdk.eth.generateSignRequest(
            requestId,
            signData,
            dataType,
            60,
            path,
            xfp,
            address,
            origin
        )
    }

    private fun genCosmosQRCode(): UREncoder {
        val accounts = ArrayList<CosmosAccount>();
        accounts.add(
            CosmosAccount(
                path = "m/44'/118'/0'/0/0",
                xfp = "f23f9fd2",
                address = "4c2a59190413dff36aba8e6ac130c7a691cfb79f"
            )
        )
        val cosmosSignRequest = CosmosSignRequest(
            requestId = "7AFD5E09-9267-43FB-A02E-08C4A09417EC",
            signData = "7B226163636F756E745F6E756D626572223A22323930353536222C22636861696E5F6964223A226F736D6F2D746573742D34222C22666565223A7B22616D6F756E74223A5B7B22616D6F756E74223A2231303032222C2264656E6F6D223A22756F736D6F227D5D2C22676173223A22313030313936227D2C226D656D6F223A22222C226D736773223A5B7B2274797065223A22636F736D6F732D73646B2F4D736753656E64222C2276616C7565223A7B22616D6F756E74223A5B7B22616D6F756E74223A223132303030303030222C2264656E6F6D223A22756F736D6F227D5D2C2266726F6D5F61646472657373223A226F736D6F31667334396A7867797A30306C78363436336534767A767838353667756C64756C6A7A6174366D222C22746F5F61646472657373223A226F736D6F31667334396A7867797A30306C78363436336534767A767838353667756C64756C6A7A6174366D227D7D5D2C2273657175656E6365223A2230227D",
            accounts = accounts,
            origin = "Keplr",
            dataType = KeystoneCosmosSDK.DataType.Amino
        )

        // tx to ur encoder
        val sdk = KeystoneSDK()
        KeystoneSDK.maxFragmentLen = 500
        return sdk.cosmos.generateSignRequest(cosmosSignRequest)
    }

    private fun genBtcQRCode(): UREncoder {
        val psbt = "70736274FF01007102000000011F907E04BF09D19C9AA9A6A3146F8FFB1FC9E23B581CB40A36F28C0128554D930100000000FFFFFFFF02E80300000000000016001491428416B0F516840B67E56B58C17F2938B843863BC004000000000016001442E7376B213643A36241071463132C77D94B223E000000000001011F45C9040000000000160014192864CD17C8BBC87B24BF812D7EB55A1B05797F220603945B57AD4BAE8836CCA9A54DDE4C759F5B4CEB0E55478031294DCEFE363EABAE18707EED6C540000800000008000000080010000001E0000000000220202AF541BCB89E5384AD417CB2F25C7DAB06FF47AD18F7E30EA3DAE2B769BEC77E418707EED6C540000800000008000000080010000001F00000000".chunked(2).map { it.toInt(16).toByte() }.toByteArray()
        val sdk = KeystoneSDK()
        KeystoneSDK.maxFragmentLen = 300
        return sdk.btc.generatePSBT(psbt)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageView: ImageView = view.findViewById(R.id.qrcode)

        // tx to ur encoder, to generate qr code
        val qr = genEthQRCode()
        imageView.setImageBitmap(displayQRCode(qr.nextPart()))

        val inst = this
        val timer = Timer()
        val task = object : TimerTask() {
            override fun run() {
                imageView.setImageBitmap(inst.displayQRCode(qr.nextPart()))
            }
        }
        timer.schedule(task, 0, 100)

        binding.buttonNext.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_ScannerFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
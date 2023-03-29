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
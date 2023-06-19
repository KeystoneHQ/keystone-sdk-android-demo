package com.keystone.sdk.demo

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Spinner
import androidx.navigation.fragment.findNavController
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.keystone.module.AptosAccount
import com.keystone.module.AptosSignRequest
import com.keystone.module.ArweaveSignRequest
import com.keystone.module.CardanoCertKey
import com.keystone.module.CardanoSignRequest
import com.keystone.module.CardanoUtxo
import com.keystone.module.CashInput
import com.keystone.module.CashTx
import com.keystone.module.CosmosAccount
import com.keystone.module.CosmosSignRequest
import com.keystone.module.EthSignRequest
import com.keystone.module.Input
import com.keystone.module.KeystoneSignRequest
import com.keystone.module.LtcTx
import com.keystone.module.NearSignRequest
import com.keystone.module.Output
import com.keystone.module.SolSignRequest
import com.keystone.module.SuiAccount
import com.keystone.module.SuiSignRequest
import com.keystone.sdk.KeystoneCosmosSDK
import com.keystone.module.TokenInfo
import com.keystone.module.TronSignRequest
import com.keystone.module.UTXO
import com.keystone.sdk.KeystoneAptosSDK
import com.keystone.sdk.KeystoneArweaveSDK
import com.keystone.sdk.KeystoneEthereumSDK
import com.keystone.sdk.KeystoneSDK
import com.keystone.sdk.KeystoneSolanaSDK
import com.keystone.sdk.KeystoneSuiSDK
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
    private val timer = Timer()
    private var timerTask: TimerTask? = null

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

    private fun genSolanaQRCode(): UREncoder {
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
        return sdk.sol.generateSignRequest(SolSignRequest(
            requestId, signData, path, xfp, address, origin, signType
        ))
    }

    private fun genEthereumQRCode(): UREncoder {
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
        return sdk.eth.generateSignRequest(EthSignRequest(
            requestId, signData, dataType, 60, path, xfp, address, origin
        ))
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

    private fun genBitcoinQRCode(): UREncoder {
        val psbt = "70736274FF01007102000000011F907E04BF09D19C9AA9A6A3146F8FFB1FC9E23B581CB40A36F28C0128554D930100000000FFFFFFFF02E80300000000000016001491428416B0F516840B67E56B58C17F2938B843863BC004000000000016001442E7376B213643A36241071463132C77D94B223E000000000001011F45C9040000000000160014192864CD17C8BBC87B24BF812D7EB55A1B05797F220603945B57AD4BAE8836CCA9A54DDE4C759F5B4CEB0E55478031294DCEFE363EABAE18707EED6C540000800000008000000080010000001E0000000000220202AF541BCB89E5384AD417CB2F25C7DAB06FF47AD18F7E30EA3DAE2B769BEC77E418707EED6C540000800000008000000080010000001F00000000".chunked(2).map { it.toInt(16).toByte() }.toByteArray()
        val sdk = KeystoneSDK()
        KeystoneSDK.maxFragmentLen = 300
        return sdk.btc.generatePSBT(psbt)
    }

    private fun genTronQRCode(): UREncoder {
        val requestId = "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d"
        val signData = "0a0207902208e1b9de559665c6714080c49789bb2c5aae01081f12a9010a31747970652e676f6f676c65617069732e636f6d2f70726f746f636f6c2e54726967676572536d617274436f6e747261637412740a15418dfec1cde1fe6a9ec38a16c7d67073e3020851c0121541a614f803b6fd780986a42c78ec9c7f77e6ded13c2244a9059cbb0000000000000000000000009c0279f1bda9fc40a85f1b53c306602864533e73000000000000000000000000000000000000000000000000000000000098968070c0b6e087bb2c90018094ebdc03"
        val path = "m/44'/195'/0'/0/0"
        val xfp = "F23F9FD2"
        val tokenInfo = TokenInfo(
            "TRON_USDT",
            "USDT",
            6
        )
        val origin = ""
        val sdk = KeystoneSDK()
        KeystoneSDK.maxFragmentLen = 300
        return sdk.tron.generateSignRequest(TronSignRequest(
            requestId, signData, path, xfp, tokenInfo, origin
        ))
    }

    private fun genAptosQRCode(): UREncoder {
        val requestId = "17467482-2654-4058-972D-F436EFAEB38E"
        val signData = "B5E97DB07FA0BD0E5598AA3643A9BC6F6693BDDC1A9FEC9E674A461EAA00B1931248CD3D5E09500ACB7082497DEC1B2690384C535F3882ED5D84392370AD0455000000000000000002000000000000000000000000000000000000000000000000000000000000000104636F696E087472616E73666572010700000000000000000000000000000000000000000000000000000000000000010A6170746F735F636F696E094170746F73436F696E0002201248CD3D5E09500ACB7082497DEC1B2690384C535F3882ED5D84392370AD04550880969800000000000A000000000000009600000000000000ACF63C640000000002"
        val accounts = ArrayList<AptosAccount>();
        accounts.add(
            AptosAccount(
                path = "m/44'/637'/0'/0'/0'",
                xfp = "F23F9FD2",
                key = "A5E70D9183F67EC4B86EA70A14C1A385281C6AB96BDE90306C166D9F11F9D91E"
            )
        )
        val origin = "Petra"
        val sdk = KeystoneSDK()
        KeystoneSDK.maxFragmentLen = 600
        return sdk.aptos.generateSignRequest(AptosSignRequest(requestId, signData, KeystoneAptosSDK.SignType.Single, accounts, origin))
    }

    private fun genSuiQRCode(): UREncoder {
        val requestId = "17467482-2654-4058-972D-F436EFAEB38E"
        val intentMessage = "00000000000200201ff915a5e9e32fdbe0135535b6c69a00a9809aaf7f7c0275d3239ca79db20d6400081027000000000000020200010101000101020000010000ebe623e33b7307f1350f8934beb3fb16baef0fc1b3f1b92868eec3944093886901a2e3e42930675d9571a467eb5d4b22553c93ccb84e9097972e02c490b4e7a22ab73200000000000020176c4727433105da34209f04ac3f22e192a2573d7948cb2fabde7d13a7f4f149ebe623e33b7307f1350f8934beb3fb16baef0fc1b3f1b92868eec39440938869e803000000000000640000000000000000"
        val accounts = ArrayList<SuiAccount>();
        accounts.add(
            SuiAccount(
                "m/44'/784'/0'/0'/0'",
                "F23F9FD2",
                "0xebe623e33b7307f1350f8934beb3fb16baef0fc1b3f1b92868eec39440938869"
            )
        )
        val origin = "Sui Wallet"
        val sdk = KeystoneSDK()
        KeystoneSDK.maxFragmentLen = 600
        return sdk.sui.generateSignRequest(SuiSignRequest(requestId, intentMessage, accounts, origin))
    }

    private fun genLitecoinQRCode(): UREncoder {
        val inputs = ArrayList<Input>()
        inputs.add(
            Input(
                "a59bcbaaae11ba5938434e2d4348243e5e392551156c4a3e88e7bdc0b2a8f663",
                2,
                UTXO(
                    "035684d200e10bc1a3e2bd7d59e58a07f2f19ef968725e18f1ed65e13396ab9466",
                    18519750
                ),
                "m/49'/2'/0'/0/0"
            )
        )
        val outputs = ArrayList<Output>()
        outputs.add(
            Output(
                "MUfnaSqZjggTrHA2raCJ9kxpP2hM6zezKw",
                10000,
            )
        )
        outputs.add(
            Output(
                "MK9aTexgpbRuMPqGpMERcjJ8hLJbAS31Nx",
                18507500,
                true,
                "M/49'/2'/0'/0/0"
            )
        )
        val signData = LtcTx(
            inputs,
            outputs,
            2250
        )
        val sdk = KeystoneSDK()
        KeystoneSDK.maxFragmentLen = 400
        return sdk.ltc.generateSignRequest(KeystoneSignRequest(
            "cc946be2-8e4c-42be-a321-56a53a8cf516",
            signData,
            "F23F9FD2",
            "LTC Wallet"
        ))
    }

    private fun genBitcoinCashQRCode(): UREncoder {
        val inputs = ArrayList<CashInput>()
        inputs.add(
            CashInput(
                "a59bcbaaae11ba5938434e2d4348243e5e392551156c4a3e88e7bdc0b2a8f663",
                1,
                18519750,
                "03cf51a0e4f926e50177d3a662eb5cc38728828cec249ef42582e77e5503675314",
                "m/44'/5'/0'/0/0"
            )
        )
        val outputs = ArrayList<Output>()
        outputs.add(
            Output(
                "XphpGezU3DUKHk87v2DoL4r7GhZUvCvvbm",
                10000,
            )
        )
        outputs.add(
            Output(
                "XfmecwGwcPBR7pXTqrn26jTjNe8a4fvcSL",
                18507500,
                true,
                "M/44'/5'/0'/0/0"
            )
        )
        val signData = CashTx(
            inputs,
            outputs,
            2250
        )
        val sdk = KeystoneSDK()
        KeystoneSDK.maxFragmentLen = 400
        return sdk.bch.generateSignRequest(KeystoneSignRequest(
            "cc946be2-8e4c-42be-a321-56a53a8cf516",
            signData,
            "F23F9FD2",
            "BCH Wallet"
        ))
    }

    private fun genDashQRCode(): UREncoder {
        val inputs = ArrayList<CashInput>()
        inputs.add(
            CashInput(
                "a59bcbaaae11ba5938434e2d4348243e5e392551156c4a3e88e7bdc0b2a8f663",
                1,
                18519750,
                "03cf51a0e4f926e50177d3a662eb5cc38728828cec249ef42582e77e5503675314",
                "m/44'/5'/0'/0/0"
            )
        )
        val outputs = ArrayList<Output>()
        outputs.add(
            Output(
                "XphpGezU3DUKHk87v2DoL4r7GhZUvCvvbm",
                10000,
            )
        )
        outputs.add(
            Output(
                "XfmecwGwcPBR7pXTqrn26jTjNe8a4fvcSL",
                18507500,
                true,
                "M/44'/5'/0'/0/0"
            )
        )
        val signData = CashTx(
            inputs,
            outputs,
            2250
        )
        val sdk = KeystoneSDK()
        KeystoneSDK.maxFragmentLen = 400
        return sdk.dash.generateSignRequest(KeystoneSignRequest(
            "cc946be2-8e4c-42be-a321-56a53a8cf516",
            signData,
            "F23F9FD2",
            "BCH Wallet"
        ))
    }

    private fun genNearQRCode(): UREncoder {
        val requestId = "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d"
        val signData = arrayListOf("4000000039666363303732306130313664336331653834396438366231366437313339653034336566633438616464316337386633396333643266303065653938633037009FCC0720A016D3C1E849D86B16D7139E043EFC48ADD1C78F39C3D2F00EE98C07823E0CA1957100004000000039666363303732306130313664336331653834396438366231366437313339653034336566633438616464316337386633396333643266303065653938633037F0787E1CB1C22A1C63C24A37E4C6C656DD3CB049E6B7C17F75D01F0859EFB7D80100000003000000A1EDCCCE1BC2D3000000000000")
        val path = "m/44'/397'/0'"
        val xfp = "F23F9FD2"
        val account = ""
        val origin = "near wallet"

        val sdk = KeystoneSDK()
        KeystoneSDK.maxFragmentLen = 500
        val signRequest = NearSignRequest(requestId, signData, path, xfp, account, origin)
        return sdk.near.generateSignRequest(signRequest)
    }

    private fun genArweaveQRCode(): UREncoder {
        val requestId = "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d"
        val signData = "7b22666f726d6174223a322c226964223a22222c226c6173745f7478223a22675448344631615059587639314a704b6b6e39495336684877515a3141597949654352793251694f654145547749454d4d5878786e466a30656b42466c713939222c226f776e6572223a22222c2274616773223a5b7b226e616d65223a2256486c775a51222c2276616c7565223a2256484a68626e4e6d5a5849227d2c7b226e616d65223a22513278705a573530222c2276616c7565223a2251584a44623235755a574e30227d2c7b226e616d65223a22513278705a5735304c565a6c636e4e70623234222c2276616c7565223a224d5334774c6a49227d5d2c22746172676574223a226b796977315934796c7279475652777454617473472d494e3965773838474d6c592d795f4c473346784741222c227175616e74697479223a2231303030303030303030222c2264617461223a22222c22646174615f73697a65223a2230222c22646174615f726f6f74223a22222c22726577617264223a2239313037353734333836222c227369676e6174757265223a22227d"
        val masterFingerprint = "F23F9FD2"
        val account = ""
        val origin = "arweave wallet"
        val signType = KeystoneArweaveSDK.SignType.Transaction
        val saltLen = KeystoneArweaveSDK.SaltLen.Zero

        val sdk = KeystoneSDK()
        KeystoneSDK.maxFragmentLen = 500
        val signReq = ArweaveSignRequest(requestId, signData, signType, saltLen, masterFingerprint, account, origin)
        return sdk.arweave.generateSignRequest(signReq)
    }

    private fun genCardanoQRCode(): UREncoder {
        val utxos = ArrayList<CardanoUtxo>();
        utxos.add(
            CardanoUtxo(
                "4e3a6e7fdcb0d0efa17bf79c13aed2b4cb9baf37fb1aa2e39553d5bd720c5c99",
                3,
                10000000,
                "73c5da0a",
                "m/1852'/1815'/0'/0/0",
                "addr1qy8ac7qqy0vtulyl7wntmsxc6wex80gvcyjy33qffrhm7sh927ysx5sftuw0dlft05dz3c7revpf7jx0xnlcjz3g69mq4afdhv"
            )
        )
        utxos.add(
            CardanoUtxo(
                "4e3a6e7fdcb0d0efa17bf79c13aed2b4cb9baf37fb1aa2e39553d5bd720c5c99",
                4,
                18020000,
                "73c5da0a",
                "m/1852'/1815'/0'/0/1",
                "addr1qyz85693g4fr8c55mfyxhae8j2u04pydxrgqr73vmwpx3azv4dgkyrgylj5yl2m0jlpdpeswyyzjs0vhwvnl6xg9f7ssrxkz90"
            )
        )

        val certKeys = ArrayList<CardanoCertKey>();
        certKeys.add(
            CardanoCertKey(
                "e557890352095f1cf6fd2b7d1a28e3c3cb029f48cf34ff890a28d176",
                "73c5da0a",
                "m/1852'/1815'/0'/2/0"
            )
        )

        val cardanoSignRequest = CardanoSignRequest(
            "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d",
            "84a400828258204e3a6e7fdcb0d0efa17bf79c13aed2b4cb9baf37fb1aa2e39553d5bd720c5c99038258204e3a6e7fdcb0d0efa17bf79c13aed2b4cb9baf37fb1aa2e39553d5bd720c5c99040182a200581d6179df4c75f7616d7d1fd39cbc1a6ea6b40a0d7b89fea62fc0909b6c370119c350a200581d61c9b0c9761fd1dc0404abd55efc895026628b5035ac623c614fbad0310119c35002198ecb0300a0f5f6",
            utxos,
            certKeys,
            "cardano-wallet",
        )

        val sdk = KeystoneSDK()
        return sdk.cardano.generateSignRequest(cardanoSignRequest)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createChainOptions(view)
        updateQRCode(view, "BitcoinCash")

        binding.buttonNext.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_ScannerFragment)
        }
    }

    private fun updateQRCode(view: View, coin: String) {
        val imageView: ImageView = view.findViewById(R.id.qrcode)

        // tx to ur encoder, to generate qr code
        val method = this::class.java.getDeclaredMethod("gen${coin}QRCode")
        val qr = method.invoke(this) as UREncoder
        imageView.setImageBitmap(displayQRCode(qr.nextPart()))

        val inst = this
        timerTask?.cancel()
        timerTask = object : TimerTask() {
            override fun run() {
                imageView.setImageBitmap(inst.displayQRCode(qr.nextPart()))
            }
        }
        timer.schedule(timerTask, 0, 100)
    }

    private fun createChainOptions(view: View) {
        val chainSpinner = view.findViewById<Spinner>(R.id.chain_spinner)

        chainSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, v: View?, position: Int, id: Long) {
                val selectedOption = parent?.getItemAtPosition(position).toString()
                // do something with the selected option
                updateQRCode(view, selectedOption)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
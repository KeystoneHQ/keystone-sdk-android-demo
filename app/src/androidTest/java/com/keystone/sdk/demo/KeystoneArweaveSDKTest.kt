package com.keystone.sdk.demo

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.keystone.module.ArweaveSignRequest
import com.keystone.sdk.KeystoneArweaveSDK
import com.keystone.sdk.KeystoneSDK

import org.junit.Test
import org.junit.runner.RunWith

import com.keystone.sdk.KeystoneSolanaSDK
import com.sparrowwallet.hummingbird.UR
import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class KeystoneArweaveSDKTest {
    @Test
    fun parseAccount() {
        val sdk = KeystoneSDK()
        val ur = UR("arweave-crypto-account", "a3011af23f9fd202590200c65925b0066affd7be100756e9ca662f52aa23faaa5b399cba83d81f871a1b7b589b36bdbc8e4a7205b74b6720cb1b9c1fc0309a4ee5150f7ad98d83c0dd12bbf1017bc1a5173925f9be08297c50ba6745c7a35c1264302e7b08d9ab48a5473f62ff0b952d13af63dfdad5ab581efc6c6c0bebfacdbfea20e6e36f3bf28c452b35bf1b3cacf6dcd80d2655b07d6ed6704a72a0825ac1885831510befbc2fbd394133357f669a3d8c0d1b7f2eafe0b5d9104d56d962133fe44c9217bf33fe11235ea4568e958e844ab2e70fe2d1b431d162fc3e2eae5cce55d85b827b0d1448ec6010bf048db8f2ede88838d652fa06bd40d61fa4a18c86587af4b419680fbea0351ad99920322813b70ed8f098acb4ef5ea8a52c73f2208c8deff1c140f056b7b584ba85b9f8cb2b9c655e69f575b65a663a1fa3d04585d9315d10c85d4bd4f234bbf989f60af60a894e4c62e9d509ad8c8a8bd16623c1385486ee869febcb2d53b03b152e24eaa6590abb2e053432f0963b62ba5b2d179f890c126e5503c4da72b4d28b48401f6296308a032c7d8d2a2716150b4869c60bf808d2bee2e59a4838f02f750e597c5d8073d3a5e19b998272b130b7386e258136f5eec9efbc7c66dbc294c88f47e64d74588a50056a19d8c7c6edf9c16ec1a3c5b7d337d1aafa30a99e930fa7883fcafdec9021a3982a011cb34c8e061d33f800dd6259cb6e637f03684b657973746f6e65".decodeHex())
        val account = sdk.arweave.parseAccount(ur)
        assertEquals(account.masterFingerprint, "F23F9FD2".lowercase())
        assertEquals(account.keyData, "C65925B0066AFFD7BE100756E9CA662F52AA23FAAA5B399CBA83D81F871A1B7B589B36BDBC8E4A7205B74B6720CB1B9C1FC0309A4EE5150F7AD98D83C0DD12BBF1017BC1A5173925F9BE08297C50BA6745C7A35C1264302E7B08D9AB48A5473F62FF0B952D13AF63DFDAD5AB581EFC6C6C0BEBFACDBFEA20E6E36F3BF28C452B35BF1B3CACF6DCD80D2655B07D6ED6704A72A0825AC1885831510BEFBC2FBD394133357F669A3D8C0D1B7F2EAFE0B5D9104D56D962133FE44C9217BF33FE11235EA4568E958E844AB2E70FE2D1B431D162FC3E2EAE5CCE55D85B827B0D1448EC6010BF048DB8F2EDE88838D652FA06BD40D61FA4A18C86587AF4B419680FBEA0351AD99920322813B70ED8F098ACB4EF5EA8A52C73F2208C8DEFF1C140F056B7B584BA85B9F8CB2B9C655E69F575B65A663A1FA3D04585D9315D10C85D4BD4F234BBF989F60AF60A894E4C62E9D509AD8C8A8BD16623C1385486EE869FEBCB2D53B03B152E24EAA6590ABB2E053432F0963B62BA5B2D179F890C126E5503C4DA72B4D28B48401F6296308A032C7D8D2A2716150B4869C60BF808D2BEE2E59A4838F02F750E597C5D8073D3A5E19B998272B130B7386E258136F5EEC9EFBC7C66DBC294C88F47E64D74588A50056A19D8C7C6EDF9C16EC1A3C5B7D337D1AAFA30A99E930FA7883FCAFDEC9021A3982A011CB34C8E061D33F800DD6259CB6E637F".lowercase())
        assertEquals(account.device, "Keystone")
    }

    @Test
    fun parseSignature() {
        val sdk = KeystoneSDK()
        val ur = UR("arweave-signature", "a201d825509b1deb4d3b7d4bad9bdd2b0d7b3dcb6d025840d4f0a7bcd95bba1fbb1051885054730e3f47064288575aacc102fbbf6a9a14daa066991e360d3e3406c20c00a40973eff37c7d641e5b351ec4a99bfe86f335f7".decodeHex())
        val sig = sdk.arweave.parseSignature(ur)
        assertEquals(sig.signature, "d4f0a7bcd95bba1fbb1051885054730e3f47064288575aacc102fbbf6a9a14daa066991e360d3e3406c20c00a40973eff37c7d641e5b351ec4a99bfe86f335f7")
        assertEquals(sig.requestId, "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d")
    }

    @Test
    fun generateSignRequest() {
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
        val res = sdk.arweave.generateSignRequest(signReq)

        assertEquals(
            res.nextPart(),
            "ur:arweave-sign-request/oladcywzfhnetdaotpdagdndcawmgtfrkigrpmndutdnbtkgfssbjnaxhkadmtkgcpiyjljpjnhsjycpfteydwcpiniecpftcpcpdwcpjzhsjkjyhejykscpftcpioghfdeefgehhsgdhkhdkoesehgejogrjejtesgaguenisfdktgyhtehfphkkkgaihfxgmkkeygyingwihfpfeghktgafegtgthdksksjtfgimdyihjefwfgjzjsesescpdwcpjlktjtihjpcpftcpcpdwcpjyhsiojkcpfthpkgcpjthsjnihcpftcphffdjzkthtgycpdwcpkohsjzkpihcpftcphffdgeisidjtgljnhthdgacpkidwkgcpjthsjnihcpftcpgyeyksjohthgecdycpdwcpkohsjzkpihcpftcpgyhdgefyideyeckphthggldycpkidwkgcpjthsjnihcpftcpgyeyksjohthgecdygshfhtjziajtgljoideyeecpdwcpkohsjzkpihcpftcpgtgueektgsimgacpkihldwcpjyhsjpioihjycpftcpjekkinktehhkeekkjzjpkkflhfgmktjyghhsjyjkfldpgaglesihktetetflgtjzhkdpkkhegsfleofgksflfpcpdwcpjskphsjtjyinjykkcpftcpehdydydydydydydydydycpdwcpiehsjyhscpftcpcpdwcpiehsjyhshejkinknihcpftcpdycpdwcpiehsjyhshejpjljljycpftcpcpdwcpjpihkthsjpiecpftcpesehdyemecemeeeoetencpdwcpjkiniojthsjykpjpihcpftcpcpkiaaadahaeamjthsjpktihhskoihcxkthsjzjzihjydppebyjt"
        )
    }
}

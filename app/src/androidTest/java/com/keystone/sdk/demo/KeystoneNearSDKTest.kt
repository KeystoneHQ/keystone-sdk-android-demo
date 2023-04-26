package com.keystone.sdk.demo

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.keystone.module.NearSignRequest
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
class KeystoneNearSDKTest {
    @Test
    fun parseSignature() {
        val sdk = KeystoneSDK()
        val ur = UR("near-signature", "a201d825509b1deb4d3b7d4bad9bdd2b0d7b3dcb6d0281584085c578f8ca68bf8d771f0346ed68c4170df9ee9878cb76f3e2fac425c3f5793d36a741547e245c6c7ac1b9433ad5fc523d41152cac2a3726cbe134e0a0366802".decodeHex())
        val signature = sdk.near.parseSignature(ur)
        assertEquals(signature.signature, arrayListOf("85c578f8ca68bf8d771f0346ed68c4170df9ee9878cb76f3e2fac425c3f5793d36a741547e245c6c7ac1b9433ad5fc523d41152cac2a3726cbe134e0a0366802"))
        assertEquals(signature.requestId, "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d")
    }

    @Test
    fun generateSignRequest() {
        val requestId = "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d"
        val signData = arrayListOf("4000000039666363303732306130313664336331653834396438366231366437313339653034336566633438616464316337386633396333643266303065653938633037009FCC0720A016D3C1E849D86B16D7139E043EFC48ADD1C78F39C3D2F00EE98C07823E0CA1957100004000000039666363303732306130313664336331653834396438366231366437313339653034336566633438616464316337386633396333643266303065653938633037F0787E1CB1C22A1C63C24A37E4C6C656DD3CB049E6B7C17F75D01F0859EFB7D80100000003000000A1EDCCCE1BC2D3000000000000")
        val path = "m/44'/397'/0'"
        val xfp = "F23F9FD2"
        val account = ""
        val origin = "nearwallet"

        val sdk = KeystoneSDK()
        KeystoneSDK.maxFragmentLen = 500
        val signRequest = NearSignRequest(requestId, signData, path, xfp, account, origin)
        val res = sdk.near.generateSignRequest(signRequest)
        assertEquals(
            res.nextPart(),
            "ur:near-sign-request/oxadtpdagdndcawmgtfrkigrpmndutdnbtkgfssbjnaolyhdvafzaeaeaeesiyiaiadyemeydyhsdyehenieeoiaehiheteeesieetenidehenieemeheoesihdyeeeoihiyiaeeethsieieehiaemetiyeoesiaeoieeyiydydyihihesetiadyemaenesfatcxnbcmtesevsgatpjecmtsbwnnaafmztfdpmttstmyessrtdwtbawllkatlffmbnoymdjsaeaefzaeaeaeesiyiaiadyemeydyhsdyehenieeoiaehiheteeesieetenidehenieemeheoesihdyeeeoihiyiaeeethsieieehiaemetiyeoesiaeoieeyiydydyihihesetiadyemwtkskbcepasadrceiasageemveswswhfutfnpfgavarlselbkptictayhkwsrltpadaeaeaeaxaeaeaeoywesftocwsateaeaeaeaeaeaeaxtaaddyoeadlncsdwykcfadlgykaeykaocywzfhnetdahimjtihhsjpkthsjzjzihjycxcahgbg"
        )
    }
}

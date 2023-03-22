package com.keystone.sdk.demo

import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import com.keystone.sdk.KeystoneSolanaSDK
import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class KeystoneSolanaSDKTest {
    @Test
    fun parseSignature() {
        val solSignature = KeystoneSolanaSDK().parseSignature("a201d825509b1deb4d3b7d4bad9bdd2b0d7b3dcb6d025840d4f0a7bcd95bba1fbb1051885054730e3f47064288575aacc102fbbf6a9a14daa066991e360d3e3406c20c00a40973eff37c7d641e5b351ec4a99bfe86f335f7")
        assertEquals(solSignature.signature, "d4f0a7bcd95bba1fbb1051885054730e3f47064288575aacc102fbbf6a9a14daa066991e360d3e3406c20c00a40973eff37c7d641e5b351ec4a99bfe86f335f7")
        assertEquals(solSignature.requestId, "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d")
    }

    @Test
    fun generateSignRequest() {
        val requestId = "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d"
        val signData = "01000103c8d842a2f17fd7aab608ce2ea535a6e958dffa20caf669b347b911c4171965530f957620b228bae2b94c82ddd4c093983a67365555b737ec7ddc1117e61c72e0000000000000000000000000000000000000000000000000000000000000000010295cc2f1f39f3604718496ea00676d6a72ec66ad09d926e3ece34f565f18d201020200010c0200000000e1f50500000000"
        val path = "m/44'/501'/0'/0'"
        val xfp = "12121212"
        val address = ""
        val origin = "solflare"
        val signType = KeystoneSolanaSDK.SignType.Transaction

        val res = KeystoneSolanaSDK().generateSignRequest(requestId, signData, path, xfp, address, origin, signType)
        assertEquals(
            res.nextPart(),
            "ur:sol-sign-request/1-3/lpadaxcstdcyrssgcarohdfgonadtpdagdndcawmgtfrkigrpmndutdnbtkgfssbjnaohdmtadaeadaxsptpfwoewnlbtspkrpaytodmonecolwlhdurzscxsgyninqdflrhbysschcfihgubsmdkocxprderdvorhgsdijofzjl"
        )
    }
}

package com.keystone.sdk.demo

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.keystone.module.TokenInfo
import com.keystone.sdk.KeystoneSDK

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class KeystoneTronSDKTest {
    @Test
    fun parseSignature() {
        val sdk = KeystoneSDK()
        val signature = sdk.sol.parseSignature("a201d825509b1deb4d3b7d4bad9bdd2b0d7b3dcb6d02584147b1f77b3e30cfbbfa41d795dd34475865240617dd1c5a7bad526f5fd89e52cd057c80b665cc2431efab53520e2b1b92a0425033baee915df858ca1c588b0a1800")
        assertEquals(signature.signature, "47b1f77b3e30cfbbfa41d795dd34475865240617dd1c5a7bad526f5fd89e52cd057c80b665cc2431efab53520e2b1b92a0425033baee915df858ca1c588b0a1800")
        assertEquals(signature.requestId, "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d")
    }

    @Test
    fun generateSignRequest() {
        val requestId = "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d"
        val signData = "0a0207902208e1b9de559665c6714080c49789bb2c5aae01081f12a9010a31747970652e676f6f676c65617069732e636f6d2f70726f746f636f6c2e54726967676572536d617274436f6e747261637412740a1541c79f045e4d48ad8dae00e6a6714dae1e000adfcd1215410d292c98a5eca06c2085fff993996423cf66c93b2244a9059cbb0000000000000000000000009bbce520d984c3b95ad10cb4e32a9294e6338da300000000000000000000000000000000000000000000000000000000000f424070c0b6e087bb2c90018094ebdc03"
        val path = "m/44'/195'/0'/0'"
        val xfp = "12121212"
        val tokenInfo = TokenInfo(
            "TONE",
            "TronOne",
            8
        )
        val address = ""
        val origin = ""

        val sdk = KeystoneSDK()
        KeystoneSDK.maxFragmentLen = 100
        val res = sdk.tron.generateSignRequest(requestId, signData, path, xfp, tokenInfo, address, origin)
        assertEquals(
            "ur:tron-sign-request/1-5/lpadahcfadrtcygakkdwnlhdhtotadtpdagdndcawmgtfrkigrpmndutdnbtkgfssbjnaohkadmhkgcpiajljtjyjphsiajyfpieiejpihjkjkcpftcpghfwfpjlemgdglkkgrjleseehkhggojsehfxjkeygsfwfgksjeisghjoisjtfpfeeeghcpdwcpiyihihcpftehdydyosfdcwjo",
            res.nextPart(),
        )
    }
}

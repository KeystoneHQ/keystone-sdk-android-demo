package com.keystone.sdk.demo

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.keystone.sdk.KeystoneEthereumSDK
import com.keystone.sdk.KeystoneSDK

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class KeystoneEthereumSDKTest {
    @Test
    fun parseSignature() {
        val sdk = KeystoneSDK()
        val sign = sdk.eth.parseSignature("a201d82550cbad90e27e774b6b992c7b0dff204d13025841c127cf3bbd7f6e405995797d4d05bb122688598bf98d7c4b2f7813d84735363a5f7347e3c29d83244b00ff62ae0c717c0c4f7aa9de2bf96da29c1f80ac05d2061b")
        assertEquals(sign.signature, "c127cf3bbd7f6e405995797d4d05bb122688598bf98d7c4b2f7813d84735363a5f7347e3c29d83244b00ff62ae0c717c0c4f7aa9de2bf96da29c1f80ac05d2061b")
        assertEquals(sign.requestId, "cbad90e2-7e77-4b6b-992c-7b0dff204d13")
    }

    @Test
    fun generateSignRequest() {
        val requestId = "6c3633c0-02c0-4313-9cd7-e25f4f296729"
        val signData = "6578616d706c652060706572736f6e616c5f7369676e60206d657373616765"
        val dataType = KeystoneEthereumSDK.DataType.PersonalMessage
        val path = "m/44'/60'/0'/0/0"
        val xfp = "707EED6C"
        val address = ""
        val origin = "eth"

        val sdk = KeystoneSDK()
        val res = sdk.eth.generateSignRequest(requestId, signData, dataType, 60, path, xfp, address, origin)
        assertEquals(
            res.nextPart(),
            "ur:eth-sign-request/oladtpdagdjzeneortaortfxbwnstsvohegwdtiodtaohdctihkshsjnjojzihcxhnjoihjpjkjljthsjzhejkiniojthncxjnihjkjkhsioihaxaxaacsfnahtaaddyoeadlecsdwykcsfnykaeykaewkaewkaocyjokbwejzatiaihjyisyntehsfl"
        )
    }
}

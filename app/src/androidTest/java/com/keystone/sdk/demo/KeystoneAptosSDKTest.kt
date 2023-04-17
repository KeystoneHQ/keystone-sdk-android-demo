package com.keystone.sdk.demo

import androidx.collection.arrayMapOf
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.keystone.module.AptosAccount
import com.keystone.module.AptosSignRequest
import com.keystone.sdk.KeystoneAptosSDK
import com.keystone.sdk.KeystoneSDK
import com.sparrowwallet.hummingbird.UR

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class KeystoneAptosSDKTest {
    @Test
    fun parseSignature() {
        val sdk = KeystoneSDK()
        val ur = UR("aptos-signature", "a301d825509b1deb4d3b7d4bad9bdd2b0d7b3dcb6d02584047e7b510784406dfa14d9fd13c3834128b49c56ddfc28edb02c5047219779adeed12017e2f9f116e83762e86f805c7311ea88fb403ff21900e069142b1fb310e0358208e53e7b10656816de70824e3016fc1a277e77825e12825dc4f239f418ab2e04e".decodeHex())
        val sign = sdk.aptos.parseSignature(ur)
        assertEquals(sign.requestId, "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d")
        assertEquals(sign.signature, "47e7b510784406dfa14d9fd13c3834128b49c56ddfc28edb02c5047219779adeed12017e2f9f116e83762e86f805c7311ea88fb403ff21900e069142b1fb310e")
        assertEquals(sign.authenticationPublicKey, "8e53e7b10656816de70824e3016fc1a277e77825e12825dc4f239f418ab2e04e")
    }

    @Test
    fun generateSignRequest() {
        val accounts = ArrayList<AptosAccount>();
        accounts.add(
            AptosAccount(
                path = "m/44'/637'/0'/0'/0'",
                xfp = "78230804",
                key = "aa7420c68c16645775ecf69a5e2fdaa4f89d3293aee0dd280e2d97ad7b879650"
            )
        )
        accounts.add(
            AptosAccount(
                "m/44'/637'/0'/0'/1'",
                "78230805",
                "97f95acfb04f84d228dce9bda4ad7e2a5cb324d5efdd6a7f0b959e755ebb3a70"
            )
        )
        val aptosSignRequest = AptosSignRequest(
            "7AFD5E09-9267-43FB-A02E-08C4A09417EC",
            "8e53e7b10656816de70824e3016fc1a277e77825e12825dc4f239f418ab2e04e",
            KeystoneAptosSDK.SignType.Single,
            accounts,
            "Petra",
        )

        val sdk = KeystoneSDK()
        KeystoneSDK.maxFragmentLen = 400
        val res = sdk.aptos.generateSignRequest(aptosSignRequest)
        assertEquals(
            res.nextPart(),
            "ur:aptos-sign-request/oladtpdagdknzchyasmoiofxzonbdmayssnbmwchwpaohdcxmnguvdpaamhflyjnvdaydkvladjlseoektvdksdavydedauogwcnnefpleprvtglaxlftaaddyoeadlecsdwykcfaokiykaeykaeykaeykaocykscnayaataaddyoeadlecsdwykcfaokiykaeykaeykadykaocykscnayahaalfhdcxpkjycxswlkcmiehgkpwpynnyhydltnoxyanteymuplvtutdebadpmspmkgltmtgdhdcxmsythttkpfgwlrtddeuowlryoxpmkbdrhhqddktlwsutimlbbdmdnnkphyrkftjoahihgdihjyjphsamadjpimreon"
        )
    }
}
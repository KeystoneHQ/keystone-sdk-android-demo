package com.keystone.sdk.demo

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.keystone.module.SuiAccount
import com.keystone.module.SuiSignRequest
import com.keystone.sdk.KeystoneSDK
import com.keystone.sdk.KeystoneSuiSDK
import com.sparrowwallet.hummingbird.UR

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class KeystoneSuiSDKTest {
    @Test
    fun parseSignature() {
        val sdk = KeystoneSDK()
        val ur = UR("sui-signature", "A301D825509B1DEB4D3B7D4BAD9BDD2B0D7B3DCB6D025840F4B79835417490958C72492723409289B444F3AF18274BA484A9EEACA9E760520E453776E5975DF058B537476932A45239685F694FC6362FE5AF6BA714DA6505035820AEB28ECACE5C664C080E71B9EFD3D071B3DAC119A26F4E830DD6BD06712ED93F".decodeHex())
        val sign = sdk.sui.parseSignature(ur)
        assertEquals(sign.requestId, "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d")
        assertEquals(sign.signature, "f4b79835417490958c72492723409289b444f3af18274ba484a9eeaca9e760520e453776e5975df058b537476932a45239685f694fc6362fe5af6ba714da6505")
        assertEquals(sign.publicKey, "aeb28ecace5c664c080e71b9efd3d071b3dac119a26f4e830dd6bd06712ed93f")
    }

    @Test
    fun generateSignRequest() {
        val accounts = ArrayList<SuiAccount>();
        accounts.add(
            SuiAccount(
                "m/44'/784'/0'/0'/0'",
                "78230804",
                "0xebe623e33b7307f1350f8934beb3fb16baef0fc1b3f1b92868eec39440938869"
            )
        )
        accounts.add(
            SuiAccount(
                "m/44'/784'/0'/0'/1'",
                "78230805",
                "1ff915a5e9e32fdbe0135535b6c69a00a9809aaf7f7c0275d3239ca79db20d64"
            )
        )
        val suiSignRequest = SuiSignRequest(
            "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d",
            "00000200201ff915a5e9e32fdbe0135535b6c69a00a9809aaf7f7c0275d3239ca79db20d6400081027000000000000020200010101000101020000010000ebe623e33b7307f1350f8934beb3fb16baef0fc1b3f1b92868eec3944093886901a2e3e42930675d9571a467eb5d4b22553c93ccb84e9097972e02c490b4e7a22ab73200000000000020176c4727433105da34209f04ac3f22e192a2573d7948cb2fabde7d13a7f4f149ebe623e33b7307f1350f8934beb3fb16baef0fc1b3f1b92868eec39440938869e803000000000000640000000000000000",
            KeystoneSuiSDK.SignType.Single,
            accounts,
            "Sui Wallet",
        )

        val sdk = KeystoneSDK()
        KeystoneSDK.maxFragmentLen = 400
        val res = sdk.sui.generateSignRequest(suiSignRequest)
        assertEquals(
            res.nextPart(),
            "ur:sui-sign-request/oladtpdagdndcawmgtfrkigrpmndutdnbtkgfssbjnaohdtaaeaeaoaecxctytbzonwlvldluyvtbwgoecrpswnyaeptlanypelbkeaokptecnnsosntprbtieaeaybediaeaeaeaeaeaeaoaoaeadadadaeadadaoaeaeadaeaewmvacnvlfrjkatwnecbsldeernqdzocmrdwsbsseqdwnrhdeiswysrmwfzmuloinadoevlvedtdyiohlmdjsoxiowmhlgrcpgofnmusfroglmhmsmsdmaossmhqzvdoedrrleyaeaeaeaeaeaecxchjzfldifxehahtneecxneaapsfhcpvymooehgfskkfdsbdlpyuekibwoswkwngawmvacnvlfrjkatwnecbsldeernqdzocmrdwsbsseqdwnrhdeiswysrmwfzmuloinvsaxaeaeaeaeaeaeieaeaeaeaeaeaeaeaeaxadaalftaaddyoeadlecsdwykcfaxbeykaeykaeykaeykaocykscnayaataaddyoeadlecsdwykcfaxbeykaeykaeykadykaocykscnayahahlfhdcxwmvacnvlfrjkatwnecbsldeernqdzocmrdwsbsseqdwnrhdeiswysrmwfzmuloinhdcxctytbzonwlvldluyvtbwgoecrpswnyaeptlanypelbkeaokptecnnsosntprbtieamimgukpincxhghsjzjzihjyknjteomw"
        )
    }
}

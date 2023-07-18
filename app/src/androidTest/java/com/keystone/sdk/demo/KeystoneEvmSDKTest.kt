package com.keystone.sdk.demo

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.keystone.module.CosmosAccount
import com.keystone.module.CosmosSignRequest
import com.keystone.module.EvmAccount
import com.keystone.module.EvmSignRequest
import com.keystone.sdk.KeystoneCosmosSDK
import com.keystone.sdk.KeystoneEvmSDK
import com.keystone.sdk.KeystoneSDK
import com.sparrowwallet.hummingbird.UR

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class KeystoneEvmSDKTest {
    @Test
    fun parseSignature() {
        val sdk = KeystoneSDK()
        val ur = UR("evm-signature", "a201d82550057523355d514a64a481ff2200000000025840a0e2577ca16119a32f421c6a1c90fa2178a9382f30bf3575ff276fb820b32b3269d49d6bbfc82bae899f60c15de4b97f24a7ebb6d4712534829628ccfbef38bc".decodeHex())
        val cosmosSignature = sdk.evm.parseSignature(ur)
        assertEquals(cosmosSignature.requestId, "05752335-5d51-4a64-a481-ff2200000000")
        assertEquals(cosmosSignature.signature, "a0e2577ca16119a32f421c6a1c90fa2178a9382f30bf3575ff276fb820b32b3269d49d6bbfc82bae899f60c15de4b97f24a7ebb6d4712534829628ccfbef38bc")
    }

    @Test
    fun generateSignRequest() {
        val account = EvmAccount(
            "m/44'/60'/0'/0/0",
            "f23f9fd2",
            "0x860A4E746FE4FDA40E98382B2F6AFC1D4040CAC7"
        )
        val evmSignRequest = EvmSignRequest(
            "05752335-5d51-4a64-a481-ff2200000000",
            "0A9D010A9A010A1C2F636F736D6F732E62616E6B2E763162657461312E4D736753656E64127A0A2C65766D6F73317363397975617230756E3736677235633871346A3736687572347179706A6B38336E786B7735122C65766D6F73317363397975617230756E3736677235633871346A3736687572347179706A6B38336E786B77351A1C0A07617465766D6F7312113130303030303030303030303030303030127E0A590A4F0A282F65746865726D696E742E63727970746F2E76312E657468736563703235366B312E5075624B657912230A21024F7A8D64E515CCF1E0A92A7C859262F425473CF09A50EBCAF3B06B156624145312040A020801181612210A1B0A07617465766D6F7312103236323530303030303030303030303010A8B4061A0C65766D6F735F393030302D342084C68E01",
            KeystoneEvmSDK.DataType.CosmosDirect,
            9000,
            account,
            "Keplr Extension",
        )

        val sdk = KeystoneSDK()
        KeystoneSDK.maxFragmentLen = 400
        val res = sdk.evm.generateSignRequest(evmSignRequest)
        assertEquals(
            res.nextPart(),
            "ur:evm-sign-request/1-2/lpadaocfadmocyldbbnehnhdsoosadtpdagdahkpcnechlgygeieoxlyzmcpaeaeaeaeaohkadeobkntadbknyadbkcedliajljkjnjljkdmidhsjtjedmkoehidihjyhsehdmgtjkioguihjtiebgknbkdwihkojnjljkehjkiaeskkkphsjpdykpjtemeniojpeciaetjseeimemeniskpjpeejskkjoimjeeteojtksjektecbgdwihkojnjljkehjkiaeskkkphsjpdykpjtemeniojpeciaetjseeimemeniskpjpeejskkjoimjeeteojtksjekteccycebkathsjyihkojnjljkbgbyehdydydydydydydydydydydydydydydydybgkbbkhkbkgwbkdedlihjyisihjpjninsocsleax"
        )
    }
}

package com.keystone.sdk.demo

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.keystone.module.CosmosAccount
import com.keystone.module.CosmosSignRequest
import com.keystone.sdk.KeystoneCosmosSDK
import com.keystone.sdk.KeystoneSDK

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class KeystoneCosmosSDKTest {
    @Test
    fun parseSignature() {
        val sdk = KeystoneSDK()
        val cosmosSignature = sdk.cosmos.parseSignature("a301d825507afd5e09926743fba02e08c4a09417ec02584078325c2ea8d1841dbcd962e894ca6ecd5890aa4c1aa9e1eb789cd2d0e9c22ec737c2b4fb9c2defd863cadf914f538330ec42d6c30c04857ee1f06e7f2589d7d903582103f3ded94f2969d76200c6ed5db836041cc815fa62aa791e047905186c07e00275")
        assertEquals(cosmosSignature.requestId, "7afd5e09-9267-43fb-a02e-08c4a09417ec")
        assertEquals(cosmosSignature.signature, "78325c2ea8d1841dbcd962e894ca6ecd5890aa4c1aa9e1eb789cd2d0e9c22ec737c2b4fb9c2defd863cadf914f538330ec42d6c30c04857ee1f06e7f2589d7d9")
        assertEquals(cosmosSignature.publicKey, "03f3ded94f2969d76200c6ed5db836041cc815fa62aa791e047905186c07e00275")
    }

    @Test
    fun generateSignRequest() {
        val accounts = ArrayList<CosmosAccount>();
        accounts.add(
            CosmosAccount(
                path = "m/44'/118'/0'/0/0",
                xfp = "f23f9fd2",
                address = "4c2a59190413dff36aba8e6ac130c7a691cfb79f"
            )
        )
        val cosmosSignRequest = CosmosSignRequest(
            signData = "7B226163636F756E745F6E756D626572223A22323930353536222C22636861696E5F6964223A226F736D6F2D746573742D34222C22666565223A7B22616D6F756E74223A5B7B22616D6F756E74223A2231303032222C2264656E6F6D223A22756F736D6F227D5D2C22676173223A22313030313936227D2C226D656D6F223A22222C226D736773223A5B7B2274797065223A22636F736D6F732D73646B2F4D736753656E64222C2276616C7565223A7B22616D6F756E74223A5B7B22616D6F756E74223A223132303030303030222C2264656E6F6D223A22756F736D6F227D5D2C2266726F6D5F61646472657373223A226F736D6F31667334396A7867797A30306C78363436336534767A767838353667756C64756C6A7A6174366D222C22746F5F61646472657373223A226F736D6F31667334396A7867797A30306C78363436336534767A767838353667756C64756C6A7A6174366D227D7D5D2C2273657175656E6365223A2230227D",
            requestId = "7AFD5E09-9267-43FB-A02E-08C4A09417EC",
            accounts = accounts,
            origin = "Keplr",
            dataType = KeystoneCosmosSDK.DataType.Amino
        )

        val sdk = KeystoneSDK()
        KeystoneSDK.maxFragmentLen = 400
        val res = sdk.cosmos.generateSignRequest(cosmosSignRequest)
        assertEquals(
            res.nextPart(),
            "ur:cosmos-sign-request/1-2/lpadaocfadtecyfzolaoaohdwdoladtpdagdknzchyasmoiofxzonbdmayssnbmwchwpaohkadjekgcphsiaiajlkpjtjyhejtkpjnidihjpcpftcpeyesdyececencpdwcpiaishsinjtheiniecpftcpjljkjnjldpjyihjkjydpeecpdwcpiyihihcpftkgcphsjnjlkpjtjycpfthpkgcphsjnjlkpjtjycpftcpehdydyeycpdwcpieihjtjljncpftcpkpjljkjnjlcpkihldwcpiohsjkcpftcpehdydyehesencpkidwcpjnihjnjlcpftcpcpdwcpjnjkiojkcpfthpkgcpjykkjoihcpftcpiajljkjnjljkdpjkiejedlgtjkioguihjtiecpdwcpkohsjzkpihcpftkgcphsjnjlkpjtjycpfthpkgcphsjnjlkpjtjycpftcpeheydydydydydydycpdwcpiernjotadi"
        )
    }
}

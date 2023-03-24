package com.keystone.sdk.demo

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.keystone.sdk.KeystoneSDK

import org.junit.Test
import org.junit.runner.RunWith

import com.keystone.sdk.KeystoneSolanaSDK
import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class KeystoneSDKTest {
    @Test
    fun decodeQR() {
        val sdk = KeystoneSDK()
        val ur = sdk.decodeQR("UR:CRYPTO-MULTI-ACCOUNTS/OTADCYJOKBWEJZAOLYTAADDLOXAOWKAXHDCXDPFNJYDNMKYTWDKTETWZHTATVDADNYJTSPWSBDAYWNHSJEFTFSDTFMCXBEPLYKRYAMTAADDYOTADLOCSDWYKCFADYKYKAEYKAEYKAOCYJOKBWEJZAXAAASIHGUGWGSDPDYAXISGRIHKKJKJYJLJTIHSSJYKSGT")
        assertEquals(
            "A3011A707EED6C0281D9012FA402F40358202D3C742B98F9EA7738F25A07E7019A6EC8EF0B08F1616B3A3D293E2010AEF5BD06D90130A30188182CF51901F5F500F500F5021A707EED6C03040965534F4C2D3003684B657973746F6E65",
            ur?.cbor
        )
        assertEquals(
            "crypto-multi-accounts",
            ur?.type
        )
    }

    @Test
    fun parseMultiAccounts() {
        val sdk = KeystoneSDK()
        val ur = sdk.decodeQR("UR:CRYPTO-MULTI-ACCOUNTS/OTADCYJOKBWEJZAOLYTAADDLOXAOWKAXHDCXDPFNJYDNMKYTWDKTETWZHTATVDADNYJTSPWSBDAYWNHSJEFTFSDTFMCXBEPLYKRYAMTAADDYOTADLOCSDWYKCFADYKYKAEYKAEYKAOCYJOKBWEJZAXAAASIHGUGWGSDPDYAXISGRIHKKJKJYJLJTIHSSJYKSGT")
        if (ur != null) {
            val accounts = sdk.parseMultiAccounts(ur.cbor)
            println(accounts)
            assertEquals(
                "Keystone",
                accounts.device
            )
            assertEquals(
                "707eed6c",
                accounts.masterFingerprint
            )
        }
    }
}
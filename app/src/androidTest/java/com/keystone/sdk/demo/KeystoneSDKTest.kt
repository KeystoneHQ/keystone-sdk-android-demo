package com.keystone.sdk.demo

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.keystone.sdk.KeystoneSDK

import org.junit.Test
import org.junit.runner.RunWith

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

    @Test
    fun parseMultiAccountsWithXPub() {
        val sdk = KeystoneSDK()
        val accounts = sdk.parseMultiAccounts("a3011aa424853c0281d9012fa4035821034af544244d31619d773521a1a366373c485ff89de50bea543c2b14cccfbb6a500458208dc2427d8ab23caab07729f88f089a3cfa2cfffcd7d1e507f983c0d44a5dbd3506d90130a10186182cf500f500f5081a149439dc03686b657973746f6e65")
        println(accounts)
        assertEquals(
            "8dc2427d8ab23caab07729f88f089a3cfa2cfffcd7d1e507f983c0d44a5dbd35",
            accounts.keys[0].getChainCode()
        )
        assertEquals(
            "xpub6BoYPFH1MivLdh2BWZuRu6LfuaVSkVak5wsDxjjkAWcUM2QPKyeCHXMgDfRJFvKZhqA4vM5vsgcD6C5ot9eThnFHstgPntNzBLUdLeKS7Zt",
            accounts.keys[0].getExtendedPublicKey()
        )
    }
}

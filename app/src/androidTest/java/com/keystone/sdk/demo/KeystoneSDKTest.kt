package com.keystone.sdk.demo

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.keystone.sdk.KeystoneSDK
import com.sparrowwallet.hummingbird.UR

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

fun String.decodeHex(): ByteArray {
    check(length % 2 == 0) { "HexString must have an even length" }
    return chunked(2)
        .map { it.toInt(16).toByte() }
        .toByteArray()
}

fun ByteArray.toHexString(): String {
    return joinToString("") { "%02X".format(it) }
}

@RunWith(AndroidJUnit4::class)
class KeystoneSDKTest {
    @Test
    fun decodeQR() {
        val sdk = KeystoneSDK()
        val decodeResult = sdk.decodeQR("UR:CRYPTO-MULTI-ACCOUNTS/OTADCYJOKBWEJZAOLYTAADDLOXAOWKAXHDCXDPFNJYDNMKYTWDKTETWZHTATVDADNYJTSPWSBDAYWNHSJEFTFSDTFMCXBEPLYKRYAMTAADDYOTADLOCSDWYKCFADYKYKAEYKAEYKAOCYJOKBWEJZAXAAASIHGUGWGSDPDYAXISGRIHKKJKJYJLJTIHSSJYKSGT")
        assertEquals(
            "A3011A707EED6C0281D9012FA402F40358202D3C742B98F9EA7738F25A07E7019A6EC8EF0B08F1616B3A3D293E2010AEF5BD06D90130A30188182CF51901F5F500F500F5021A707EED6C03040965534F4C2D3003684B657973746F6E65",
            decodeResult.ur?.cborBytes?.toHexString()
        )
        assertEquals(
            "crypto-multi-accounts",
            decodeResult.ur?.type
        )
    }

    @Test
    fun parseMultiAccounts() {
        val sdk = KeystoneSDK()
        val decodeResult = sdk.decodeQR("UR:CRYPTO-MULTI-ACCOUNTS/OTADCYJOKBWEJZAOLYTAADDLOXAOWKAXHDCXDPFNJYDNMKYTWDKTETWZHTATVDADNYJTSPWSBDAYWNHSJEFTFSDTFMCXBEPLYKRYAMTAADDYOTADLOCSDWYKCFADYKYKAEYKAEYKAOCYJOKBWEJZAXAAASIHGUGWGSDPDYAXISGRIHKKJKJYJLJTIHSSJYKSGT")
        val ur = decodeResult.ur
        if (ur != null) {
            val accounts = sdk.parseMultiAccounts(ur)
            println(accounts)
            assertEquals(
                "Keystone",
                accounts.device
            )
            assertEquals(
                "707eed6c",
                accounts.masterFingerprint
            )
            assertEquals(
                501,
                accounts.keys[0].extra.okx.chainId
            )
        }
    }

    @Test
    fun parseZcashAccounts() {
        val sdk = KeystoneSDK()
        sdk.decodeQR("UR:ZCASH-ACCOUNTS/99-2/LPCSIAAOCFADIYCYWYCSVOFMHDQDNDJYENGEURJNZMGLCTOTRPZETKKTSRRTSNWDNTCMZSNNMYMHRPPTWYZSOTNDDLBDADTYLTGYIAVOWEROHYSWINAXJLHFBSBNAOBECEFLAMADFPCSCABKCFGMFPFZBWHECFBSASGRCWBZBBCTBZBBBDBDFZAXFEBDBTHKBBAMHPBWFYGTFWAXBKHPBKBABKGWCTFWBWCYFEHDBGGOAHBEHEATBKAEHHBWBAAHHTCFATATBDCABEATAAAEBEHLAXCKBEAMGEGABGGMADBEBTBDCTBWCEBBAXBGFWCWASGUFXHPHTAEFZAAAMBEAAGUCKGDADCTFZHKBGBBFYAHBEJYKNIECTFPGSFLFPGWGDDMUETBOX")
        sdk.decodeQR("UR:ZCASH-ACCOUNTS/191-2/LPCSRSAOCFADIYCYWYCSVOFMHDQDNDJYENGEURJNZMGLCTOTRPZETKKTSRRTSNWDNTCMZSNNMYMHRPPTWYZSOTNDDLBDADTYLTGYIAVOWEROHYSWINAXJLHFBSBNAOBECEFLAMADFPCSCABKCFGMFPFZBWHECFBSASGRCWBZBBCTBZBBBDBDFZAXFEBDBTHKBBAMHPBWFYGTFWAXBKHPBKBABKGWCTFWBWCYFEHDBGGOAHBEHEATBKAEHHBWBAAHHTCFATATBDCABEATAAAEBEHLAXCKBEAMGEGABGGMADBEBTBDCTBWCEBBAXBGFWCWASGUFXHPHTAEFZAAAMBEAAGUCKGDADCTFZHKBGBBFYAHBEJYKNIECTFPGSFLFPGWGDIOMNSWAA");
        val decodeResult = sdk.decodeQR("UR:ZCASH-ACCOUNTS/160-2/LPCSNBAOCFADIYCYWYCSVOFMHDQDOEADHDCXPEHLNSDKKBMETISBYNAXYNYNWKNSZTCLMNPYYTOESETPLNTOMWZTCMKIJNOSVAIEAOLYTARTEOOTADKKADDMKPKOINIHKTEHJTEYJSKPKOIYJZIEJKDYKOJEJPIAKNEYHSJYIEJKJSKTKSJTKPJEJPJNKTJTJOIMISKKJPKKECJTHSJEJZKTKNECJNJSKSJYEMESIMETECIEEYKTJEISENKPIYKPESKSIYJKIHJSKNHSISJZKSESIYKPIOJKESDYIAIOJSJKISJSJZIYIYKPECIAEYISIHIOKPISIAETKKJPKTIOEEETIYIHIEKTKPETIEIHEYEOKTKOKNIOKKKTKNJSKTKKIYPAKGBGZS");

        val ur = decodeResult.ur
        if (ur != null) {
            val accounts = sdk.parseZcashAccounts(ur)
            assertEquals(
                "af5d9c247e91d0cbf603f6f6f49cfc218eabf9a2c1d886ce94fc167d6da7e664",
                accounts.seedFingerprint
            )
            val account = accounts.accounts[0];

            assertEquals(
                "uview1n2quvflds0vkrcz2atdsqwxnukrmwnpjhyry5naklwz5mqxt79j85d2wkh6ufu9xfseqzahlx9fugs90cgqshqlffu5c2heguhc8yrwg48fedwu8de23wvzgywzqwyf9unjp0cja2f59t569va7t5v2wqh47g9vlsa5ac4xmehznxzzkukvh30mklu62pe4klsyzapldcse5h7fz7dl3j64wmk0fypzr3knraxm0tmpahjfhpcaatnljfllhdekwusyq5pcezsuza6qpsl463989vqw0kx5eh5avqv6g",
                account.ufvk
            )
            assertEquals(
                "666666",
                account.name
            )
            assertEquals(
                0,
                account.index
            )

        }
    }

    @Test
    fun parseMultiAccountsWithDeviceId() {
        val sdk = KeystoneSDK()
        val decodeResult = sdk.decodeQR("ur:crypto-multi-accounts/oxadcyjokbwejzaomntaaddlptaowkaxhdclaoregsnyceihdycnqdpepsielyrthfqzdwgyfpisfltsutuycsdrvdrewslywdhhpdaahdcxgmctgamnckgatdwseeoxehhngmstfgsrcejnjtwygwbdzsjzdylrlbinfwfgvwjpahtaadehoeadcsfnaoaeamtaaddyotadlncsdwykcsfnykaeykaocyjokbwejzaxaxattaaddyoeadlraewklawkaxaeaycygubklbmwasisgrihkkjkjyjljtihbkjohsiaiajlkpjtjydmjkjyhsjtiehsjpietaaddlonaowkaxhdclaxsnkbroiahfqdhfndisynmylnlfghinrhbeeslrwzfldeytcpqdpschsngrtbjofsamtaaddyotadlecsdwykcsfnykaeykaewkaewkaocyjokbwejzaxahasisgrihkkjkjyjljtihbkjkhsiaiajlkpjtjydmjzihieioihjphejzinkoihtaaddlonaowkaxhdclaotppremgwhefgfhdpyatthyzmcxzmiobwttwmstcltylybdisbahsnbvtadytlofwamtaaddyotadlecsdwykcsfnykadykaewkaewkaocyjokbwejzaxahasisgrihkkjkjyjljtihbkjkhsiaiajlkpjtjydmjzihieioihjphejzinkoihtaaddlonaowkaxhdclaootqdhdytihlaspclghlkteaekolpltjkeebzayurbgtkfpvtzotknttlgmimimdnamtaaddyotadlecsdwykcsfnykaoykaewkaewkaocyjokbwejzaxahasisgrihkkjkjyjljtihbkjkhsiaiajlkpjtjydmjzihieioihjphejzinkoihtaaddlonaowkaxhdclaolkvddieevahhplaalnzolaahgukgoskonszsfzcnvwbtnsjntnjzimykjltdgukpamtaaddyotadlecsdwykcsfnykaxykaewkaewkaocyjokbwejzaxahasisgrihkkjkjyjljtihbkjkhsiaiajlkpjtjydmjzihieioihjphejzinkoihtaaddlonaowkaxhdclaxvskkwnashseornhnftzmzorfolglkktbdtsfqddskkuoaejkambntpdydsaeaotdamtaaddyotadlecsdwykcsfnykaaykaewkaewkaocyjokbwejzaxahasisgrihkkjkjyjljtihbkjkhsiaiajlkpjtjydmjzihieioihjphejzinkoihtaaddlonaowkaxhdclaoaywnwdwpaamdamrobzghwkdtlyataxmybkrdhedkmwjohyvdmdveinvdtsimpasaamtaaddyotadlecsdwykcsfnykahykaewkaewkaocyjokbwejzaxahasisgrihkkjkjyjljtihbkjkhsiaiajlkpjtjydmjzihieioihjphejzinkoihtaaddlonaowkaxhdclaxzcdeimkbfrpsbaplsngmgwwylowslspsenidemjyqdjlassnolbwuoiardvsiegmamtaaddyotadlecsdwykcsfnykamykaewkaewkaocyjokbwejzaxahasisgrihkkjkjyjljtihbkjkhsiaiajlkpjtjydmjzihieioihjphejzinkoihtaaddlonaowkaxhdclaxtsytflpehsoniaztrkghnbtphlaatirsjzsgbzctlklrlgfysrcnsennemamhlmhamtaaddyotadlecsdwykcsfnykatykaewkaewkaocyjokbwejzaxahasisgrihkkjkjyjljtihbkjkhsiaiajlkpjtjydmjzihieioihjphejzinkoihtaaddlonaowkaxhdclaxdaeemnktrndnbkcpmejsgmjetslgiopstoytenwedrbasatolpjktbyavepmfzhdamtaaddyotadlecsdwykcsfnykayykaewkaewkaocyjokbwejzaxahasisgrihkkjkjyjljtihbkjkhsiaiajlkpjtjydmjzihieioihjphejzinkoihtaaddlonaowkaxhdclaoadlkzsknsrmhdstlhetbhelyrdcpimbwurcmpaayvlosneurhpvllndltabwbystamtaaddyotadlecsdwykcsfnykasykaewkaewkaocyjokbwejzaxahasisgrihkkjkjyjljtihbkjkhsiaiajlkpjtjydmjzihieioihjphejzinkoihtaaddlosaowkaxhdclaozegonygseswytkoelprlcerhlsvwatbkmhnnwsfpttdtlgyniswpjsmwgrcxbemkaahdcxjlrnwzdsyayleejedrptltehneeygdsechlnlnktamtlfyeebnnbvdjstsmtzmfgahtaadehoeadaeaoaeamtaaddyotadlncsdwykaeykaeykaocyjokbwejzaxaxaycylgkblodeasisgrihkkjkjyjljtihtaaddlosaowkaxhdclaostecmufhtyisoneyjofyqzloonamaddmytpmoyonhtcwosfsdskocxetwdrybzfmaahdcxktinvttbatcpjzdsykjshknefloxkevsaxdiiokgjneevyutzcaerdcaftfhjntsahtaadehoeadaeaoaeamtaaddyotadlncsehykaeykaeykaocyjokbwejzaxaxaycyfthduyjzasisgrihkkjkjyjljtihtaaddlosaowkaxhdclaxrhbkpmytsbhptkbkfwdagtcfjohnoelsbntlwzlrhdnsahuokpoepdfmhhaahlceaahdcxhhknwmnslflrfgtbolotskkbcabgcyeyvwgyotjspytlwygmbsstprmypkoskgpaahtaadehoeadaeaoaeamtaaddyotadlncsghykaeykaeykaocyjokbwejzaxaxaycymuenyapdasisgrihkkjkjyjljtihaxjzjeihkkjkjyjljtihcxgdjpjlaaksdeeyeteeemeciaetieetdyiyeniadyenidhsiyidiheeenhsemieehemecdyiyeoiyiaiyeyeceneciyemihpyndmh")
        val ur = decodeResult.ur
        if (ur != null) {
            val accounts = sdk.parseMultiAccounts(ur)
            println(accounts)
            assertEquals(
                "keystone Pro",
                accounts.device
            )
            assertEquals(
                "28475c8d80f6c06bafbe46a7d1750f3fcf2565f7",
                accounts.deviceId
            )
        }
    }

    @Test
    fun parseMultiAccountsWithXPub() {
        val sdk = KeystoneSDK()
        val ur = UR("crypto-multi-accounts", "a3011aa424853c0281d9012fa4035821034af544244d31619d773521a1a366373c485ff89de50bea543c2b14cccfbb6a500458208dc2427d8ab23caab07729f88f089a3cfa2cfffcd7d1e507f983c0d44a5dbd3506d90130a10186182cf500f500f5081a149439dc03686b657973746f6e65".decodeHex())
        val accounts = sdk.parseMultiAccounts(ur)
        println(accounts)
        assertEquals(
            "8dc2427d8ab23caab07729f88f089a3cfa2cfffcd7d1e507f983c0d44a5dbd35",
            accounts.keys[0].getChainCode()
        )
        assertEquals(
            "xpub6BoYPFH1MivLdh2BWZuRu6LfuaVSkVak5wsDxjjkAWcUM2QPKyeCHXMgDfRJFvKZhqA4vM5vsgcD6C5ot9eThnFHstgPntNzBLUdLeKS7Zt",
            accounts.keys[0].getExtendedPublicKey()
        )

        println(accounts.keys[0])

        assertEquals(
            0,
            accounts.keys[0].extra.okx.chainId
        )
    }

    @Test
    fun parseAccount() {
        val sdk = KeystoneSDK()
        val ur = UR("crypto-hdkey", "A902F4035821032F547FD525B6D83CC2C44F939CC1425FA1E98D97D26B00F9E2D04952933C5128045820B92B17B393612FC8E945E5C5389439CA0C0A28C3076C060B15C3F9F6523A9D1905D90131A201183C020006D90130A30186182CF5183CF500F5021A52006EA0030307D90130A2018400F480F40300081AEA156CD409684B657973746F6E650A706163636F756E742E7374616E64617264".decodeHex())
        val account = sdk.parseAccount(ur)
        println(account)

        assertEquals("ETH", account.chain)
        assertEquals("m/44'/60'/0'", account.path)
        assertEquals("Keystone", account.name)
        assertEquals("52006ea0", account.xfp)
        assertEquals("032f547fd525b6d83cc2c44f939cc1425fa1e98d97d26b00f9e2d04952933c5128", account.publicKey)
        assertEquals("b92b17b393612fc8e945e5c5389439ca0c0a28c3076c060b15c3f9f6523a9d19", account.getChainCode())
        assertEquals("account.standard", account.note)
        assertEquals("xpub6DNZW8rxA28BGjbbjJEHhsAbYYyFpJJWN52biwHvLS1kTeJ2j2xzcf41oTQ7o5h5udaHqb6NLxei5tZ8fbjBziZajSY4TmrPC4ftcK8pLZ3", account.getExtendedPublicKey())
    }

    @Test
    fun parseCryptoAccount() {
        val sdk = KeystoneSDK()
        val ur = UR("crypto-account", "A2011A52006EA0028AD9012FA502F403582102FEF03A2BD3DE113F1DC1CDB1E69AA4D935DC3458D542D796F5827ABBB1A58B5E06D90130A3018A182CF5183CF500F500F400F4021A52006EA0030509684B657973746F6E650A736163636F756E742E6C65646765725F6C697665D9012FA502F4035821033F1EDDF1D1BB2762FCFA67FBC35E12DC9968CD2587ADA055210E84F780C1109A06D90130A3018A182CF5183CF501F500F400F4021A52006EA0030509684B657973746F6E650A736163636F756E742E6C65646765725F6C697665D9012FA502F403582102C5FCF766AD77A0C254834D57CE3E6120A2BE5C266E9BABE8A047D1A53CB34F9E06D90130A3018A182CF5183CF502F500F400F4021A52006EA0030509684B657973746F6E650A736163636F756E742E6C65646765725F6C697665D9012FA502F403582102CD0B648CF944CBA7E6BE97BF1F17F0EAB7B9E600D181C421B3BCE6E7F6D941F006D90130A3018A182CF5183CF503F500F400F4021A52006EA0030509684B657973746F6E650A736163636F756E742E6C65646765725F6C697665D9012FA502F40358210351F72104E737E94C7CC66E33307C74D5BBF19216800157AD34EBFE232F23C75106D90130A3018A182CF5183CF504F500F400F4021A52006EA0030509684B657973746F6E650A736163636F756E742E6C65646765725F6C697665D9012FA502F403582102037F8C5FC1074E654FF11619A8BF28DCC3DB5D037191F08EB5722252AF57A4A606D90130A3018A182CF5183CF505F500F400F4021A52006EA0030509684B657973746F6E650A736163636F756E742E6C65646765725F6C697665D9012FA502F403582103A441895DFBE9C7B3BF8EBA0CE461465A14350D902DF163A0B3F06E4F4843E54F06D90130A3018A182CF5183CF506F500F400F4021A52006EA0030509684B657973746F6E650A736163636F756E742E6C65646765725F6C697665D9012FA502F403582102A8DCDF480733A5B7FB331C9464B7E0EDF5206D8581FE3E26BFD6DE38C8063D4C06D90130A3018A182CF5183CF507F500F400F4021A52006EA0030509684B657973746F6E650A736163636F756E742E6C65646765725F6C697665D9012FA502F4035821037A1A6A48B09D4E3A01223B37C9D1212D8DA20746302009956168E1EA3BD3E0C806D90130A3018A182CF5183CF508F500F400F4021A52006EA0030509684B657973746F6E650A736163636F756E742E6C65646765725F6C697665D9012FA502F403582103C6F04A813F23799940B6FA44C6CA48ABE04DE9FBB8133B7342DBABC95B0EA48106D90130A3018A182CF5183CF509F500F400F4021A52006EA0030509684B657973746F6E650A736163636F756E742E6C65646765725F6C697665".decodeHex())
        val multiAccounts = sdk.parseCryptoAccount(ur)
        println(multiAccounts)

        assertEquals(10, multiAccounts.keys.size)

        val firstKey = multiAccounts.keys.first()
        assertEquals("52006ea0", firstKey.xfp)
        assertEquals("m/44'/60'/0'/0/0", firstKey.path)
        assertEquals("account.ledger_live", firstKey.note)
        assertEquals("02fef03a2bd3de113f1dc1cdb1e69aa4d935dc3458d542d796f5827abbb1a58b5e", firstKey.publicKey)
        assertEquals("", firstKey.getChainCode())
        assertEquals("", firstKey.getExtendedPublicKey())
    }

    @Test
    fun getUncompressedKey() {
        val sdk = KeystoneSDK()
        val uncompressedKey = sdk.getUncompressedKey("02fef03a2bd3de113f1dc1cdb1e69aa4d935dc3458d542d796f5827abbb1a58b5e")
        val expectedResult = "04fef03a2bd3de113f1dc1cdb1e69aa4d935dc3458d542d796f5827abbb1a58b5ebdffecfa6587da3216d50114700e5e314650cc2268e9fcb6ac31593bcc71d178"

        assertEquals(expectedResult, uncompressedKey)
    }
}

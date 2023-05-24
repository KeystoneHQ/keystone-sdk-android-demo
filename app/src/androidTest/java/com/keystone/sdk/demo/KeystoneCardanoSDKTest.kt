package com.keystone.sdk.demo

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.keystone.module.CardanoCertKey
import com.keystone.module.CardanoSignRequest
import com.keystone.module.CardanoUtxo
import com.keystone.sdk.KeystoneAptosSDK
import com.keystone.sdk.KeystoneSDK
import com.sparrowwallet.hummingbird.UR

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class KeystoneCardanoSDKTest {
    @Test
    fun parseSignature() {
        val sdk = KeystoneSDK()
        val ur = UR("cardano-signature", "a301d825509b1deb4d3b7d4bad9bdd2b0d7b3dcb6d02584047e7b510784406dfa14d9fd13c3834128b49c56ddfc28edb02c5047219779adeed12017e2f9f116e83762e86f805c7311ea88fb403ff21900e069142b1fb310e0358208e53e7b10656816de70824e3016fc1a277e77825e12825dc4f239f418ab2e04e".decodeHex())
        val sign = sdk.cardano.parseSignature(ur)
        assertEquals(sign.requestId, "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d")
        assertEquals(sign.witnessSet, "47e7b510784406dfa14d9fd13c3834128b49c56ddfc28edb02c5047219779adeed12017e2f9f116e83762e86f805c7311ea88fb403ff21900e069142b1fb310e")
    }

    @Test
    fun generateSignRequest() {
        val utxos = ArrayList<CardanoUtxo>();
        utxos.add(
            CardanoUtxo(
                "4e3a6e7fdcb0d0efa17bf79c13aed2b4cb9baf37fb1aa2e39553d5bd720c5c99",
                3,
                10000000,
                "73c5da0a",
                "m/1852'/1815'/0'/0/0",
                "addr1qy8ac7qqy0vtulyl7wntmsxc6wex80gvcyjy33qffrhm7sh927ysx5sftuw0dlft05dz3c7revpf7jx0xnlcjz3g69mq4afdhv"
            )
        )
        utxos.add(
            CardanoUtxo(
                "4e3a6e7fdcb0d0efa17bf79c13aed2b4cb9baf37fb1aa2e39553d5bd720c5c99",
                4,
                18020000,
                "73c5da0a",
                "m/1852'/1815'/0'/0/1",
                "addr1qyz85693g4fr8c55mfyxhae8j2u04pydxrgqr73vmwpx3azv4dgkyrgylj5yl2m0jlpdpeswyyzjs0vhwvnl6xg9f7ssrxkz90"
            )
        )

        val certKeys = ArrayList<CardanoCertKey>();
        certKeys.add(
            CardanoCertKey(
                "e557890352095f1cf6fd2b7d1a28e3c3cb029f48cf34ff890a28d176",
                "73c5da0a",
                "m/1852'/1815'/0'/2/0"
            )
        )

        val cardanoSignRequest = CardanoSignRequest(
            "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d",
            "84a400828258204e3a6e7fdcb0d0efa17bf79c13aed2b4cb9baf37fb1aa2e39553d5bd720c5c99038258204e3a6e7fdcb0d0efa17bf79c13aed2b4cb9baf37fb1aa2e39553d5bd720c5c99040182a200581d6179df4c75f7616d7d1fd39cbc1a6ea6b40a0d7b89fea62fc0909b6c370119c350a200581d61c9b0c9761fd1dc0404abd55efc895026628b5035ac623c614fbad0310119c35002198ecb0300a0f5f6",
            utxos,
            certKeys,
            "cardano-wallet",
        )

        val sdk = KeystoneSDK()
        KeystoneSDK.maxFragmentLen = 400
        val res = sdk.cardano.generateSignRequest(cardanoSignRequest)
        assertEquals(
            res.nextPart(),
            "ur:cardano-sign-request/1-2/lpadaocfaojkcydstoptbghkadftonadtpdagdndcawmgtfrkigrpmndutdnbtkgfssbjnaohdoylroxaelflfhdcxglftjtlbuopftiwsoykgylnsbwpltdqzsbndpeemzocyoevlmdgutlryjpbnhhnlaxlfhdcxglftjtlbuopftiwsoykgylnsbwpltdqzsbndpeemzocyoevlmdgutlryjpbnhhnlaaadlfoeaehdcahskkurgskpylhsjnkicttensrfcyjtolqzbkbtkgldzeoldlrtmhndjzemadcfsrgdoeaehdcahssopfsokoctttuoaaaapytlhyztldgddsidlugdecpsidfnhsgwrdtiehadcfsrgdaocfmnsbaxaenbykynaxlftaaynlonadhdcxglftjtlbuopftiwsoykgylnsbwpltdqzsbndpeemzocyoevlmdgutlryjpbnhhnlaoaxaxcyaemkmtlaaataaddyoeadlecfatfnykcfatchykaeykaewkaewkaocyjksktnbkahksiohsieiejpehjskkethsiaemjsjskkdykojykpjzkkjzemktjtjyjnjkksiaenktihksetdyiokoiakkimkkeoeojsiyiyjpisjnemhysedtsp"
        )
    }
}
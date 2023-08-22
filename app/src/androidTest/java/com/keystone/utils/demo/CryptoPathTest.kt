package com.keystone.utils.demo

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.keystone.module.PathItem
import com.keystone.utils.CryptoPath

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class CryptoPathTest {
    @Test
    fun parseHDPath() {
        val hdPath = "m/44'/60'/0'/10/1"
        val path = CryptoPath().parseHDPath(hdPath)

        assertEquals(path.purpose, PathItem(44, true))
        assertEquals(path.coinType, PathItem(60, true))
        assertEquals(path.account, PathItem(0, true))
        assertEquals(path.change, PathItem(10, false))
        assertEquals(path.addressIndex, PathItem(1, false))
    }

    @Test
    fun parseXpubPath() {
        val hdPath = "m/44'/0'"
        val path = CryptoPath().parseHDPath(hdPath)

        assertEquals(path.purpose?.index, 44)
        assertEquals(path.coinType?.index, 0)
        assertNull(path.account)
        assertNull(path.change)
        assertNull(path.addressIndex)
    }

    @Test
    fun parseRandomString() {
        val hdPath = "abcd"
        val path = CryptoPath().parseHDPath(hdPath)

        assertNull(path.purpose)
        assertNull(path.coinType)
        assertNull(path.account)
        assertNull(path.change)
        assertNull(path.addressIndex)
    }
}
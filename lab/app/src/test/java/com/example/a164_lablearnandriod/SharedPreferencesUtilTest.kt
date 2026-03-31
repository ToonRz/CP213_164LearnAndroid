package com.example.a164_lablearnandriod

import android.content.Context
import android.content.SharedPreferences
import com.example.a164_lablearnandriod.utils.SharedPreferencesUtil
import io.mockk.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SharedPreferencesUtilTest {

    private lateinit var prefs: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    @Before
    fun setup() {
        val field = SharedPreferencesUtil::class.java.getDeclaredField("sharedPreferences")
        field.isAccessible = true
        field.set(SharedPreferencesUtil, null)

        prefs = mockk()
        editor = mockk()
        val ctx = mockk<Context>()

        every { editor.putString(any(), any()) } returns editor
        every { editor.putInt(any(), any()) } returns editor
        every { editor.putBoolean(any(), any()) } returns editor
        every { editor.remove(any()) } returns editor
        every { editor.clear() } returns editor
        every { editor.apply() } just runs
        every { prefs.edit() } returns editor
        every { ctx.getSharedPreferences("my_app_prefs", Context.MODE_PRIVATE) } returns prefs

        SharedPreferencesUtil.init(ctx)
    }

    @Test
    fun `saveString and getString`() {
        every { prefs.getString("name", "") } returns "John"
        SharedPreferencesUtil.saveString("name", "John")
        verify { editor.putString("name", "John") }
        assertEquals("John", SharedPreferencesUtil.getString("name"))
    }

    @Test
    fun `saveInt and getInt`() {
        every { prefs.getInt("age", 0) } returns 25
        SharedPreferencesUtil.saveInt("age", 25)
        verify { editor.putInt("age", 25) }
        assertEquals(25, SharedPreferencesUtil.getInt("age"))
    }

    @Test
    fun `saveBoolean and getBoolean`() {
        every { prefs.getBoolean("logged_in", false) } returns true
        SharedPreferencesUtil.saveBoolean("logged_in", true)
        verify { editor.putBoolean("logged_in", true) }
        assertTrue(SharedPreferencesUtil.getBoolean("logged_in"))
    }

    @Test
    fun `getString returns default when key missing`() {
        every { prefs.getString("x", "fallback") } returns "fallback"
        assertEquals("fallback", SharedPreferencesUtil.getString("x", "fallback"))
    }

    @Test
    fun `remove calls editor remove`() {
        SharedPreferencesUtil.remove("name")
        verify { editor.remove("name") }
        verify { editor.apply() }
    }

    @Test
    fun `clearAll calls editor clear`() {
        SharedPreferencesUtil.clearAll()
        verify { editor.clear() }
        verify { editor.apply() }
    }
}

package com.example.a164_lablearnandriod

import org.junit.Assert.*
import org.junit.Test

/**
 * Tests for LocationData data class.
 * Pure JVM tests - no Android dependencies needed.
 */
class LocationDataTest {

    @Test
    fun `create LocationData with valid values`() {
        val data = LocationData(13.7563, 100.5018, 5.0f)
        assertEquals(13.7563, data.latitude, 0.0001)
        assertEquals(100.5018, data.longitude, 0.0001)
        assertEquals(5.0f, data.accuracy, 0.001f)
    }

    @Test
    fun `LocationData equality for same values`() {
        val a = LocationData(13.7563, 100.5018, 5.0f)
        val b = LocationData(13.7563, 100.5018, 5.0f)
        assertEquals(a, b)
    }

    @Test
    fun `LocationData inequality for different values`() {
        val a = LocationData(13.7563, 100.5018, 5.0f)
        val b = LocationData(14.0, 101.0, 10.0f)
        assertNotEquals(a, b)
    }

    @Test
    fun `LocationData copy creates modified instance`() {
        val original = LocationData(13.7563, 100.5018, 5.0f)
        val modified = original.copy(latitude = 14.0)
        assertEquals(14.0, modified.latitude, 0.0001)
        assertEquals(100.5018, modified.longitude, 0.0001)
        assertEquals(5.0f, modified.accuracy, 0.001f)
    }
}

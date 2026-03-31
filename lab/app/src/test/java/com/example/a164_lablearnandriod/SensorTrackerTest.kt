package com.example.a164_lablearnandriod

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import io.mockk.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SensorTrackerTest {

    private lateinit var sensorManager: SensorManager
    private lateinit var sensor: Sensor
    private lateinit var tracker: SensorTracker

    @Before
    fun setup() {
        val ctx = mockk<Context>()
        sensorManager = mockk(relaxed = true)
        sensor = mockk()
        every { ctx.getSystemService(Context.SENSOR_SERVICE) } returns sensorManager
        every { sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) } returns sensor
        tracker = SensorTracker(ctx)
    }

    @Test
    fun `initial sensorData is zeros`() {
        val data = tracker.sensorData.value
        assertArrayEquals(floatArrayOf(0f, 0f, 0f), data, 0.001f)
    }

    @Test
    fun `start registers sensor listener`() {
        tracker.start()
        verify {
            sensorManager.registerListener(
                any<SensorEventListener>(),
                sensor,
                SensorManager.SENSOR_DELAY_UI
            )
        }
    }

    @Test
    fun `stop unregisters sensor listener`() {
        tracker.stop()
        verify {
            sensorManager.unregisterListener(any<SensorEventListener>())
        }
    }
}

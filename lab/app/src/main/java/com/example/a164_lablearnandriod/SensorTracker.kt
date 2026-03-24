package com.example.a164_lablearnandriod

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * SensorTracker — Repository Layer
 *
 * หน้าที่: ติดต่อกับ Hardware โดยตรง (SensorManager) และแปลงข้อมูลดิบ
 * เป็น StateFlow ให้ ViewModel นำไปใช้ต่อ
 *
 * ทำไมต้องแยกออกมา?
 *   - Single Responsibility: แต่ละชั้น (Layer) มีหน้าที่ชัดเจน
 *   - Testability: สามารถ mock SensorTracker ในการทดสอบ ViewModel ได้
 *   - ห้ามยัด SensorEventListener ไว้ใน Composable โดยตรง!
 */
class SensorTracker(context: Context) : SensorEventListener {

    private val sensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private val accelerometer: Sensor? =
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    // ─── StateFlow ที่ expose ค่า x, y, z ออกไป ───
    // ค่าตั้งต้น [0f, 0f, 0f] หมายถึง x=0, y=0, z=0
    private val _sensorData = MutableStateFlow(floatArrayOf(0f, 0f, 0f))
    val sensorData: StateFlow<FloatArray> = _sensorData.asStateFlow()

    /** เรียกเมื่อหน้าจอเปิด → เริ่มรับค่าจากเซ็นเซอร์ */
    fun start() {
        accelerometer?.let {
            sensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_UI  // อัปเดตทุก ~60ms เหมาะกับ UI
            )
        }
    }

    /** เรียกเมื่อหน้าจอปิด → หยุดรับค่า ประหยัดแบตเตอรี่ */
    fun stop() {
        sensorManager.unregisterListener(this)
    }

    // ─── Callback จาก Android OS ทุกครั้งที่เซ็นเซอร์มีค่าใหม่ ───
    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            // .clone() สำคัญ! ป้องกัน SensorEvent.values ถูก reuse โดย OS
            _sensorData.value = event.values.clone()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // ไม่ใช้ในตัวอย่างนี้
    }
}

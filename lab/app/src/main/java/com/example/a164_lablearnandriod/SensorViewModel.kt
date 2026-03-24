package com.example.a164_lablearnandriod

import android.app.Application
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// Task 2/3: ViewModel ที่ใช้ StateFlow เก็บค่า x, y, z จาก Accelerometer
// ใช้ AndroidViewModel เพื่อให้เข้าถึง Application Context สำหรับ SensorManager ได้
class SensorViewModel(application: Application) : AndroidViewModel(application), SensorEventListener {

    private val sensorManager =
        application.getSystemService(Application.SENSOR_SERVICE) as SensorManager

    private val accelerometer: Sensor? =
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    // MutableStateFlow (private) ไว้อัปเดตข้างใน ViewModel
    // ค่าตั้งต้น FloatArray(3) = [0f, 0f, 0f] หมายถึง x=0, y=0, z=0
    private val _accelerometerData = MutableStateFlow(FloatArray(3) { 0f })

    // StateFlow (public) ที่ Composable จะ observe ผ่าน collectAsState()
    val accelerometerData: StateFlow<FloatArray> = _accelerometerData.asStateFlow()

    // เรียกเมื่อหน้าจอเปิดขึ้นมา -> ลงทะเบียนรับข้อมูลเซ็นเซอร์
    fun startListening() {
        accelerometer?.let {
            sensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_UI  // อัปเดตความเร็วระดับ UI (~60ms)
            )
        }
    }

    // เรียกเมื่อหน้าจอปิด -> ยกเลิกการรับข้อมูลเพื่อประหยัดแบตเตอรี่
    fun stopListening() {
        sensorManager.unregisterListener(this)
    }

    // Callback เมื่อเซ็นเซอร์อ่านค่าใหม่ได้
    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            // .clone() สำคัญมาก! ป้องกันปัญหา SensorEvent.values array ถูก reuse
            _accelerometerData.value = event.values.clone()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // ไม่ต้องใช้ในตัวอย่างนี้
    }

    // ถูกเรียกอัตโนมัติเมื่อ ViewModel ถูกทำลาย (เช่น Activity ถูกปิด)
    override fun onCleared() {
        super.onCleared()
        stopListening()
    }
}

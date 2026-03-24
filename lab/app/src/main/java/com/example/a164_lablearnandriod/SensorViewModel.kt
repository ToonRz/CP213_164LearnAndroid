package com.example.a164_lablearnandriod

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.StateFlow

/**
 * SensorViewModel — ViewModel Layer
 *
 * หน้าที่: เป็นสะพานระหว่าง Repository (SensorTracker) กับ UI (Composable)
 * - ถือ instance ของ SensorTracker (Repository)
 * - Expose StateFlow ให้ UI นำไป observe
 * - จัดการ Lifecycle ของ SensorTracker ผ่าน start/stop
 *
 * ✅ ถูกต้อง: ViewModel ไม่รู้จัก SensorManager โดยตรง
 * ✅ ถูกต้อง: ViewModel ไม่รู้จัก Composable โดยตรง
 */
class SensorViewModel(application: Application) : AndroidViewModel(application) {

    // Repository Layer — SensorTracker เป็นตัวคุยกับ Hardware โดยตรง
    private val sensorTracker = SensorTracker(application)
    private val locationTracker = LocationTracker(application)

    // Expose StateFlow ของ Tracker ออกไปให้ UI ใช้
    // UI จะเห็นแค่ StateFlow (read-only) ไม่สามารถแก้ค่าได้โดยตรง
    val sensorData: StateFlow<FloatArray> = sensorTracker.sensorData
    val locationData: StateFlow<LocationData?> = locationTracker.locationData

    /** เรียกเมื่อหน้าจอเปิด → Delegate ไปที่ SensorTracker & LocationTracker */
    fun startListening() {
        sensorTracker.start()
        locationTracker.start()
    }

    /** เรียกเมื่อหน้าจอปิด → Delegate ไปที่ SensorTracker & LocationTracker */
    fun stopListening() {
        sensorTracker.stop()
        locationTracker.stop()
    }

    /** ถูกเรียกอัตโนมัติเมื่อ ViewModel ถูกทำลาย → หยุดเซ็นเซอร์ */
    override fun onCleared() {
        super.onCleared()
        sensorTracker.stop()
        locationTracker.stop()
    }
}

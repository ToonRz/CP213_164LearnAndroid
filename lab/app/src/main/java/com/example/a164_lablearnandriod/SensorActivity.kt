package com.example.a164_lablearnandriod

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * SensorActivity — UI Entry Point สำหรับ Task 2/3
 */
class SensorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SensorScreen()
        }
    }
}

/**
 * SensorScreen — UI Layer (Jetpack Compose)
 *
 * หน้าที่: แสดงผลค่าเซ็นเซอร์แบบ Real-time
 * - รับ SensorViewModel ผ่าน viewModel() helper (Compose สร้างให้อัตโนมัติ)
 * - ใช้ collectAsState() แปลง StateFlow → Compose State
 * - ใช้ DisposableEffect จัดการ start/stop ตาม Lifecycle
 *
 * ❌ Anti-Pattern: ห้ามใส่ SensorEventListener ไว้ใน Composable!
 * ✅ ถูกต้อง: Composable รู้จักแค่ ViewModel เท่านั้น
 */
@Composable
fun SensorScreen(viewModel: SensorViewModel = viewModel()) {

    // ─────────────────────────────────────────────────────────
    // collectAsState() = "สมัครรับข่าวสาร" จาก StateFlow
    // ทุกครั้งที่ SensorTracker อัปเดตค่าใหม่:
    //   SensorTracker → StateFlow → ViewModel → collectAsState → Recompose!
    // ─────────────────────────────────────────────────────────
    val sensorData by viewModel.sensorData.collectAsState()

    // DisposableEffect: รันโค้ดตอนเข้า Composition และ onDispose ตอนออก
    // ใช้แทน lifecycleObserver ใน Composable — ปลอดภัยและถูกต้อง
    DisposableEffect(Unit) {
        viewModel.startListening()   // หน้าจอเปิด → เริ่มเซ็นเซอร์
        onDispose {
            viewModel.stopListening() // หน้าจอปิด → หยุดเซ็นเซอร์ ประหยัดแบต
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ─── Header ───
        Text(
            text = "Task 2/3: Sensors + MVVM",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "SensorTracker → ViewModel → collectAsState()",
            fontSize = 11.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))

        // ─── Card แสดงค่าเซ็นเซอร์ ───
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Accelerometer",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(12.dp))
                Divider()
                Spacer(modifier = Modifier.height(12.dp))

                // แสดงค่าแต่ละแกน — sensorData[0]=X, [1]=Y, [2]=Z
                SensorAxisRow(axis = "X", value = sensorData[0], color = Color(0xFFE53935))
                Spacer(modifier = Modifier.height(8.dp))
                SensorAxisRow(axis = "Y", value = sensorData[1], color = Color(0xFF43A047))
                Spacer(modifier = Modifier.height(8.dp))
                SensorAxisRow(axis = "Z", value = sensorData[2], color = Color(0xFF1E88E5))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "หน่วย: m/s²  •  อัปเดตทุก ~60ms",
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}

/** Composable ย่อย: แสดงชื่อแกนและค่าตัวเลข */
@Composable
fun SensorAxisRow(axis: String, value: Float, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "แกน $axis",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = color
        )
        Text(
            // แสดงค่าทศนิยม 4 ตำแหน่ง พร้อม padding หน้าเพื่อให้ตัวเลขไม่กระโดด
            text = "${String.format("%+.4f", value)} m/s²",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
        )
    }
}

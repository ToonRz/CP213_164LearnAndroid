package com.example.a164_lablearnandriod

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

// Task 2/3: Activity หลักสำหรับแสดงค่า Accelerometer แบบ Real-time
class SensorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SensorScreen()
        }
    }
}

@Composable
fun SensorScreen(sensorViewModel: SensorViewModel = viewModel()) {
    // collectAsState() แปลง StateFlow -> State ของ Compose
    // ทุกครั้งที่ StateFlow มีค่าใหม่ Compose จะวาดหน้าจอใหม่อัตโนมัติ
    val sensorData by sensorViewModel.accelerometerData.collectAsState()

    // DisposableEffect จัดการ Lifecycle:
    // - ตอนที่ Composable เข้าสู่หน้าจอ -> startListening()
    // - ตอนที่ Composable ออกจากหน้าจอ -> stopListening() (via onDispose)
    DisposableEffect(Unit) {
        sensorViewModel.startListening()
        onDispose {
            sensorViewModel.stopListening()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Task 2/3: Accelerometer",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Real-time via StateFlow + collectAsState",
            fontSize = 13.sp,
            color = androidx.compose.ui.graphics.Color.Gray
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Card แสดงค่าแต่ละแกน
        Card(
            modifier = Modifier.padding(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                AxisRow(label = "X", value = sensorData[0])
                Spacer(modifier = Modifier.height(8.dp))
                AxisRow(label = "Y", value = sensorData[1])
                Spacer(modifier = Modifier.height(8.dp))
                AxisRow(label = "Z", value = sensorData[2])
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "หน่วย: m/s²", fontSize = 14.sp, color = androidx.compose.ui.graphics.Color.Gray)
    }
}

// Composable ย่อยสำหรับแสดงค่าแต่ละแกน
@Composable
fun AxisRow(label: String, value: Float) {
    Text(
        text = "แกน $label :  ${String.format("%.4f", value)}  m/s²",
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium
    )
}

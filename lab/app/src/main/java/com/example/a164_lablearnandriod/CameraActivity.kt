package com.example.a164_lablearnandriod

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat

// Task 1: ขอ Permission กล้อง แล้วเปิดกล้องด้วย TakePicturePreview แสดงผล Bitmap บนหน้าจอ
class CameraActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // State เก็บภาพที่ถ่ายมา (null = ยังไม่มีภาพ)
            var capturedImage by remember { mutableStateOf<Bitmap?>(null) }

            // ตรวจว่ามี Permission กล้องอยู่แล้วไหม
            var hasCameraPermission by remember {
                mutableStateOf(
                    ContextCompat.checkSelfPermission(
                        this, Manifest.permission.CAMERA
                    ) == PackageManager.PERMISSION_GRANTED
                )
            }

            // Launcher สำหรับเปิดกล้องถ่ายรูป -> รับ Bitmap กลับมา
            val takePictureLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.TakePicturePreview()
            ) { bitmap: Bitmap? ->
                capturedImage = bitmap   // อัปเดต State ให้ Compose วาดหน้าจอใหม่
            }

            // Launcher สำหรับขอ Permission กล้อง
            val permissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                hasCameraPermission = isGranted
                if (isGranted) {
                    takePictureLauncher.launch() // ได้รับอนุญาต -> เปิดกล้องทันที
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
                    text = "Task 1: Camera",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))

                // แสดงภาพที่ถ่ายมา (ถ้ามี)
                if (capturedImage != null) {
                    Image(
                        bitmap = capturedImage!!.asImageBitmap(),
                        contentDescription = "Captured Image",
                        modifier = Modifier
                            .size(280.dp)
                            .border(2.dp, Color.Gray, RoundedCornerShape(8.dp))
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                } else {
                    Text(
                        text = "ยังไม่มีรูปภาพ",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Button(onClick = {
                    if (hasCameraPermission) {
                        // มี Permission แล้ว -> เปิดกล้องได้เลย
                        takePictureLauncher.launch()
                    } else {
                        // ยังไม่มี Permission -> ขอก่อน
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                }) {
                    Text(text = if (capturedImage != null) "ถ่ายรูปใหม่" else "เปิดกล้อง")
                }
            }
        }
    }
}

package com.example.a164_lablearnandriod

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage

// Task 1: Gallery & Permission Flow
// ใช้ Implicit Intent ผ่าน GetContent() เพื่อเปิดแกลเลอรีของระบบ
class GalleryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // ─────────────────────────────────────────────
            // กำหนด Permission ที่ต้องขอตาม Android version
            //   API 33+ (Android 13)  → READ_MEDIA_IMAGES
            //   API 30-32 (Android 11-12) → READ_EXTERNAL_STORAGE (deprecated แต่ยังใช้ได้)
            // ─────────────────────────────────────────────
            val requiredPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Manifest.permission.READ_MEDIA_IMAGES
            } else {
                Manifest.permission.READ_EXTERNAL_STORAGE
            }

            // State เก็บ Uri ของรูปที่เลือก (null = ยังไม่ได้เลือก)
            var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

            // ตรวจสอบว่ามี Permission อยู่แล้วหรือยัง (ตรวจทุกครั้งที่ Recompose)
            var hasReadPermission by remember {
                mutableStateOf(
                    ContextCompat.checkSelfPermission(
                        this, requiredPermission
                    ) == PackageManager.PERMISSION_GRANTED
                )
            }

            // ─────────────────────────────────────────────
            // Launcher 1: เปิดแกลเลอรีรับ Uri ของรูปภาพกลับมา
            //   - ใส่ "image/*" เพื่อกรองเฉพาะไฟล์รูปภาพ
            //   - รับ Uri กลับมา (ไม่ใช่ Bitmap เหมือน TakePicturePreview)
            // ─────────────────────────────────────────────
            val galleryLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.GetContent()
            ) { uri: Uri? ->
                selectedImageUri = uri   // อัปเดต State → Compose วาดรูปใหม่อัตโนมัติ
            }

            // ─────────────────────────────────────────────
            // Launcher 2: ขอ Permission การอ่านไฟล์รูปภาพ
            // ─────────────────────────────────────────────
            val permissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                hasReadPermission = isGranted
                if (isGranted) {
                    // ได้รับอนุญาต → เปิดแกลเลอรีทันที
                    galleryLauncher.launch("image/*")
                }
            }

            // ─────── UI ───────
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Task 1: Gallery",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Permission Flow + Coil AsyncImage",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(24.dp))

                // ─── แสดงรูปภาพที่เลือก ───
                if (selectedImageUri != null) {
                    // AsyncImage จาก Coil: รับ Uri โดยตรง โหลดรูปในพื้นหลังอัตโนมัติ
                    AsyncImage(
                        model = selectedImageUri,
                        contentDescription = "Selected Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .border(2.dp, Color.LightGray, RoundedCornerShape(12.dp))
                    )
                } else {
                    // Placeholder ตอนที่ยังไม่มีรูป
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .border(2.dp, Color.LightGray, RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "ยังไม่ได้เลือกรูปภาพ\nกดปุ่มด้านล่างเพื่อเลือก",
                            color = Color.Gray,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // ─── ปุ่มหลัก ───
                Button(onClick = {
                    if (hasReadPermission) {
                        // ✅ มี Permission แล้ว → เปิดแกลเลอรีได้เลย
                        galleryLauncher.launch("image/*")
                    } else {
                        // ❌ ยังไม่มี Permission → ขอก่อน
                        permissionLauncher.launch(requiredPermission)
                    }
                }) {
                    Text(text = if (selectedImageUri != null) "เลือกรูปอื่น" else "เลือกรูปภาพ")
                }
            }
        }
    }
}

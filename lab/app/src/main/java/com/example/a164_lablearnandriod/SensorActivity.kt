package com.example.a164_lablearnandriod

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.math.abs

class SensorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { SensorScreen() }
    }
}

@Composable
fun SensorScreen(viewModel: SensorViewModel = viewModel()) {
    val sensorData by viewModel.sensorData.collectAsState()
    val locationData by viewModel.locationData.collectAsState()

    val context = androidx.compose.ui.platform.LocalContext.current
    var hasLocationPermission by androidx.compose.runtime.remember {
        androidx.compose.runtime.mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        hasLocationPermission = isGranted
        if (isGranted) viewModel.startListening()
    }

    DisposableEffect(Unit) {
        viewModel.startListening()
        onDispose { viewModel.stopListening() }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(colors = listOf(NavyDark, NavyMid)))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Text("📡", fontSize = 40.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Accelerometer", color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Bold)
            Text("Task 2/3 — StateFlow + MVVM", color = Color.White.copy(alpha = 0.5f), fontSize = 13.sp)
            Spacer(modifier = Modifier.height(32.dp))

            // Sensor Cards
            SensorBar(label = "X", value = sensorData[0], color = Color(0xFFFF6B6B))
            Spacer(modifier = Modifier.height(12.dp))
            SensorBar(label = "Y", value = sensorData[1], color = Color(0xFF6BCB77))
            Spacer(modifier = Modifier.height(12.dp))
            SensorBar(label = "Z", value = sensorData[2], color = Color(0xFF4D96FF))

            Spacer(modifier = Modifier.height(28.dp))

            // Raw Values Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardBg),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        "Raw Values (m/s²)",
                        color = Color.White.copy(alpha = 0.5f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        RawValueCell("X", sensorData[0], Color(0xFFFF6B6B))
                        RawValueCell("Y", sensorData[1], Color(0xFF6BCB77))
                        RawValueCell("Z", sensorData[2], Color(0xFF4D96FF))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Location Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardBg),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Live Location",
                        color = Color.White.copy(alpha = 0.5f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    if (hasLocationPermission) {
                        locationData?.let { loc ->
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                                RawValueCell("Lat", loc.latitude.toFloat(), Color(0xFFFFB26B))
                                RawValueCell("Lon", loc.longitude.toFloat(), Color(0xFFFFB26B))
                                RawValueCell("Acc(m)", loc.accuracy, Color(0xFFE91E63))
                            }
                        } ?: Text("รอสัญญาณ GPS...", color = Color.White.copy(alpha = 0.7f))
                    } else {
                        androidx.compose.material3.Button(
                            onClick = { permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION) },
                            colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = IndigoAccent)
                        ) {
                            Text("อนุญาตให้เข้าถึงตำแหน่ง", color = Color.White)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "SensorTracker → StateFlow → collectAsState() → UI",
                color = Color.White.copy(alpha = 0.25f),
                fontSize = 10.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun SensorBar(label: String, value: Float, color: Color) {
    // Clamp value ไว้ที่ ±20 m/s² เพื่อแสดง progress bar
    val normalised = (abs(value) / 20f).coerceIn(0f, 1f)
    val animatedProgress by animateFloatAsState(
        targetValue = normalised,
        animationSpec = tween(durationMillis = 80),
        label = "sensor_$label"
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = CardBg),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("แกน $label", color = color, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(
                    String.format("%+.4f", value),
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Monospace
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = color,
                trackColor = color.copy(alpha = 0.15f)
            )
        }
    }
}

@Composable
fun RawValueCell(label: String, value: Float, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, color = color, fontSize = 13.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            String.format("%.2f", value),
            color = Color.White,
            fontSize = 15.sp,
            fontFamily = FontFamily.Monospace
        )
    }
}

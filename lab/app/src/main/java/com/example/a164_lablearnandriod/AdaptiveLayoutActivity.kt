package com.example.a164_lablearnandriod

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.a164_lablearnandriod.ui.theme._164_LabLearnAndriodTheme

class AdaptiveLayoutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _164_LabLearnAndriodTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AdaptiveProfileScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun AdaptiveProfileScreen(modifier: Modifier = Modifier) {
    // Mission 8: Responsive Design with BoxWithConstraints
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val isTablet = maxWidth >= 600.dp

        if (isTablet) {
            // Row Layout for Tablet
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                verticalAlignment = Alignment.CenterHorizontally,
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                ProfileImage(Modifier.size(200.dp))
                ProfileInfo(Modifier.weight(1f))
            }
        } else {
            // Column Layout for Mobile
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ProfileImage(Modifier.size(150.dp))
                Spacer(modifier = Modifier.height(24.dp))
                ProfileInfo(Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
fun ProfileImage(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(Color.Gray),
        contentAlignment = Alignment.Center
    ) {
        Text("IMG", color = Color.White, style = MaterialTheme.typography.displaySmall)
    }
}

@Composable
fun ProfileInfo(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text("John Doe", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Android Developer with 5 years of experience in building modern Jetpack Compose applications.",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

package com.example.a164_lablearnandriod

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.a164_lablearnandriod.ui.theme._164_LabLearnAndriodTheme

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val data = intent.getStringExtra("EXTRA_DATA") ?: "No data"
        
        setContent {
            _164_LabLearnAndriodTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DetailScreen(
                        data = data,
                        modifier = Modifier.padding(innerPadding),
                        onClose = {
                            finish()
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                                overrideActivityTransition(OVERRIDE_TRANSITION_CLOSE, R.anim.stay, R.anim.slide_down)
                            } else {
                                overridePendingTransition(R.anim.stay, R.anim.slide_down)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun DetailScreen(data: String, modifier: Modifier = Modifier, onClose: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFE3F2FD)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Detail Activity", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Data: $data", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onClose) {
            Text("Close (Slide Down)")
        }
    }
}

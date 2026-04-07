package com.example.a164_lablearnandriod

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityOptionsCompat
import com.example.a164_lablearnandriod.ui.theme._164_LabLearnAndriodTheme

class TransitionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _164_LabLearnAndriodTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TransitionScreen(
                        modifier = Modifier.padding(innerPadding),
                        onOpenDetail = {
                            val intent = Intent(this, DetailActivity::class.java).apply {
                                putExtra("EXTRA_DATA", "Hello from MainActivity!")
                            }
                            val options = ActivityOptionsCompat.makeCustomAnimation(
                                this,
                                R.anim.slide_up,
                                R.anim.stay
                            )
                            startActivity(intent, options.toBundle())
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun TransitionScreen(modifier: Modifier = Modifier, onOpenDetail: () -> Unit) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Transition Demo (Mission 7)", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onOpenDetail) {
            Text("Open Detail (Slide Up)")
        }
    }
}

package com.example.a164_lablearnandriod

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.a164_lablearnandriod.ui.theme._164_LabLearnAndriodTheme
class MenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, MainActivity::class.java))
                }) {
                    Text("RPGCardActivity")
                }
                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, LstActivity::class.java))
                }) {
                    Text("PokedexActivity")
                }
                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, MainActivity2::class.java))
                }) {
                    Text("LifeCycleComposeActivity")
                }
                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, SharedPreferencesActivity::class.java))
                }) {
                    Text("SharedPreferencesActivity")
                }
                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, GalleryActivity::class.java))
                }) {
                    Text("GalleryActivity (Task 1)")
                }
            }
        }
    }
}
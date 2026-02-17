package com.example.a164_lablearnandriod

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.a164_lablearnandriod.ui.theme._164_LabLearnAndriodTheme
import com.example.a164_lablearnandriod.utils.SharedPreferencesUtil

class SharedPreferencesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 1. เชื่อมต่อเครื่องมือเข้ากับ Context ของแอพ
        SharedPreferencesUtil.init(this)
        // การบันทึกค่า (เช่น เมื่อกดปุ่ม Save)
        SharedPreferencesUtil.saveString("user_name", "Narawitch Makprang")
        SharedPreferencesUtil.saveBoolean("is_dark_mode", true)

// การดึงค่ามาใช้งาน (เช่น เมื่อเปิดแอพขึ้นมาใหม่)
        val name = SharedPreferencesUtil.getString("user_name")
        val darkMode = SharedPreferencesUtil.getBoolean("is_dark_mode")

        enableEdgeToEdge()
        setContent {
            _164_LabLearnAndriodTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = name,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    _164_LabLearnAndriodTheme {
        Greeting("Android")
    }
}
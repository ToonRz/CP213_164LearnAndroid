package com.example.a164_lablearnandriod

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Adb
import androidx.compose.material.icons.filled.Animation
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material.icons.filled.VerticalAlignBottom
import androidx.compose.material.icons.filled.Web
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ─── Design Tokens ───────────────────────────────────────────
val NavyDark   = Color(0xFF0D1B2A)
val NavyMid    = Color(0xFF1B2A4A)
val IndigoAccent = Color(0xFF6C63FF)
val VioletAccent = Color(0xFF9C27B0)
val TealAccent   = Color(0xFF00BCD4)
val CardBg       = Color(0xFF1E2D50)
// ─────────────────────────────────────────────────────────────

class MenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MenuScreen() }
    }

    @Composable
    fun MenuScreen() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(NavyDark, NavyMid)
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // ─── Header ───
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(IndigoAccent.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🚀", fontSize = 36.sp)
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "CP213 Lab",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Android Development Activities",
                    color = Color.White.copy(alpha = 0.5f),
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.height(32.dp))

                // ─── Menu Cards ───
                val menus = listOf(
                    Triple("RPG Card", "Character stats & UI", Icons.Filled.Star) to
                            { startActivity(Intent(this@MenuActivity, MainActivity::class.java)) },
                    Triple("Pokédex", "Fetch Pokémon from API", Icons.Filled.Image) to
                            { startActivity(Intent(this@MenuActivity, LstActivity::class.java)) },
                    Triple("Lifecycle Demo", "Compose lifecycle hooks", Icons.Filled.Timeline) to
                            { startActivity(Intent(this@MenuActivity, MainActivity2::class.java)) },
                    Triple("Shared Prefs", "Save & load key-value data", Icons.Filled.Storage) to
                            { startActivity(Intent(this@MenuActivity, SharedPreferencesActivity::class.java)) },
                    Triple("Gallery (Task 1)", "Permission + image picker", Icons.Filled.Camera) to
                            { startActivity(Intent(this@MenuActivity, GalleryActivity::class.java)) },
                    Triple("Sensors (Task 2/3)", "MVVM + Accelerometer", Icons.Filled.Sensors) to
                            { startActivity(Intent(this@MenuActivity, SensorActivity::class.java)) },
                    
                    // --- New Advanced Missions ---
                    Triple("Mission 1", "Animations & Motion", Icons.Filled.Animation) to
                            { startActivity(Intent(this@MenuActivity, AnimationActivity::class.java)) },
                    Triple("Mission 2", "Complex Lists & Pagination", Icons.Filled.List) to
                            { startActivity(Intent(this@MenuActivity, Part2Activity::class.java)) },
                    Triple("Mission 3", "Canvas Graphics & Effects", Icons.Filled.Brush) to
                            { startActivity(Intent(this@MenuActivity, CanvasActivity::class.java)) },
                    Triple("Mission 4", "Advanced Gestures", Icons.Filled.TouchApp) to
                            { startActivity(Intent(this@MenuActivity, GestureActivity::class.java)) },
                    Triple("Mission 5", "Compose Side Effects", Icons.Filled.Bolt) to
                            { startActivity(Intent(this@MenuActivity, SideEffectActivity::class.java)) },
                    Triple("Mission 6", "WebView (View Interop)", Icons.Filled.Web) to
                            { startActivity(Intent(this@MenuActivity, WebViewActivity::class.java)) },
                    Triple("Mission 7", "Activity Transitions", Icons.Filled.OpenInNew) to
                            { startActivity(Intent(this@MenuActivity, TransitionActivity::class.java)) },
                    Triple("Mission 8", "Adaptive Layouts", Icons.Filled.Adb) to
                            { startActivity(Intent(this@MenuActivity, AdaptiveLayoutActivity::class.java)) },
                    Triple("Extra", "Collapsing Toolbar", Icons.Filled.VerticalAlignBottom) to
                            { startActivity(Intent(this@MenuActivity, CollapsingToolbarActivity::class.java)) },
                )

                androidx.compose.foundation.lazy.LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 32.dp)
                ) {
                    items(menus.size) { index ->
                        val (info, action) = menus[index]
                        MenuCard(title = info.first, subtitle = info.second, icon = info.third, onClick = action)
                    }
                }
            }
        }
    }
}

@Composable
fun MenuCard(title: String, subtitle: String, icon: ImageVector, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(IndigoAccent.copy(alpha = 0.25f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = IndigoAccent, modifier = Modifier.size(22.dp))
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                Text(text = subtitle, color = Color.White.copy(alpha = 0.45f), fontSize = 12.sp)
            }
            Text(text = "›", color = IndigoAccent, fontSize = 22.sp, fontWeight = FontWeight.Light)
        }
    }
}
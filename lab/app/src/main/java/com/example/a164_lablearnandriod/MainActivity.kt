package com.example.a164_lablearnandriod

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Column(modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)
                .padding(32.dp)){
                //hp
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .background(Color.White)

                ){
                    Text(
                        text = "hp",
                        modifier = Modifier
                            .align(alignment = Alignment.CenterStart)
                            .fillMaxWidth(fraction = 0.64F)
                            .background(color =Color.Red)
                            .padding(8.dp)
                    )
                }
                //image
                Image(
                    painter = painterResource(id = R.drawable.ic_profile),
                    contentDescription = "ic_profile",
                    modifier = Modifier
                        .size(400.dp)
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 16.dp)
                        .clickable{
                            startActivity(Intent(this@MainActivity, LstActivity::class.java))
                        }
                )
                var str by remember { mutableStateOf(8) }
                var Agl by remember { mutableStateOf(10) }
                var Int by remember { mutableStateOf(15) }
                //status
                Row(modifier = Modifier.fillMaxWidth().background(Color.Gray),
                    horizontalArrangement = Arrangement.SpaceBetween)
                {
                    Column() {
                        Button(onClick = {
                            str = str + 1
                        }, modifier = Modifier.shadow(8.dp, CircleShape)) {
                            Image(
                                painter = painterResource(id = R.drawable.uparrow),
                                contentDescription = "uparrow",
                                modifier = Modifier
                                    .size(40.dp)
                            )
                        }
                        Text(text = "str", fontSize = 32.sp)
                        Text(text = str.toString(), fontSize = 32.sp)
                        Button(onClick = {
                            str = str - 1
                        }, modifier = Modifier.shadow(8.dp, CircleShape)) {
                            Image(
                                painter = painterResource(id = R.drawable.downarrow),
                                contentDescription = "downarrow",
                                modifier = Modifier
                                    .size(40.dp)
                            )
                        }
                    }
                    Column() {
                        Button(onClick = {
                            Agl = Agl + 1
                        },modifier = Modifier.shadow(8.dp, CircleShape)) {
                            Image(
                                painter = painterResource(id = R.drawable.uparrow),
                                contentDescription = "uparrow",
                                modifier = Modifier
                                    .size(40.dp)
                            )
                        }
                        Text(text = "Agl", fontSize = 32.sp)
                        Text(text = Agl.toString(), fontSize = 32.sp)
                        Button(onClick = {
                            Agl = Agl - 1
                        }, modifier = Modifier.shadow(8.dp, CircleShape)) {
                            Image(
                                painter = painterResource(id = R.drawable.downarrow),
                                contentDescription = "downarrow",
                                modifier = Modifier
                                    .size(40.dp)
                            )
                        }
                    }
                    Column() {
                        Button(onClick = {
                            Int = Int + 1
                        }, modifier = Modifier.shadow(8.dp, CircleShape)) {
                            Image(
                                painter = painterResource(id = R.drawable.uparrow),
                                contentDescription = "uparrow",
                                modifier = Modifier
                                    .size(40.dp)
                            )
                        }
                        Text(text = "Int", fontSize = 32.sp)
                        Text(text = Int.toString(), fontSize = 32.sp)
                        Button(onClick = {
                            Int = Int - 1
                        }, modifier = Modifier.shadow(8.dp, CircleShape)) {
                            Image(
                                painter = painterResource(id = R.drawable.downarrow),
                                contentDescription = "downarrow",
                                modifier = Modifier
                                    .size(40.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}



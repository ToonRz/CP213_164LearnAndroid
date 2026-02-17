package com.example.a164_lablearnandriod

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

class LstActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ListScreen(PokemonViewModel())
        }
    }
}

@Composable
fun ListScreen(viewModel: PokemonViewModel) {

    val pokemonList by viewModel.pokemonList.collectAsState()


    Column(modifier = Modifier.fillMaxSize().background(Color.Red).padding(16.dp)) {
        Column(modifier = Modifier.fillMaxSize().background(Color.Gray).padding(16.dp)) {
            LazyColumn(modifier = Modifier.fillMaxSize().background(Color.White).padding(16.dp)) {
                items(pokemonList){ item ->
                    Row() {
                        Text(text = item.entry_number.toString(), fontSize = 16.sp)
                        Text(text = item.pokemon_species.name, fontSize = 16.sp)

                        val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-iii/firered-leafgreen/${item.pokemon_species}.png"

                        AsyncImage(
                            model = imageUrl,
                            contentDescription = "Sprite of ${item.pokemon_species}",
                            modifier = Modifier.size(64.dp),
                            placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                            error = painterResource(id = R.drawable.ic_launcher_background)
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ListScreen(PokemonViewModel())
}
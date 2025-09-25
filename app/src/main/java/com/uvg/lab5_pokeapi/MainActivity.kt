package com.uvg.lab5_pokeapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.uvg.lab5_pokeapi.ui.PokeApp
import com.uvg.lab5_pokeapi.ui.theme.Lab5pokeapiTheme
import com.uvg.lab5_pokeapi.ui.theme.PokedexDarkRed

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab5pokeapiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = PokedexDarkRed
                ) {
                    PokeApp()
                }
            }
        }
    }
}

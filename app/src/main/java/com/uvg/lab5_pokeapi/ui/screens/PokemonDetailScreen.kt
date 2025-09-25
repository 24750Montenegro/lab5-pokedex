package com.uvg.lab5_pokeapi.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.uvg.lab5_pokeapi.R
import com.uvg.lab5_pokeapi.network.Pokemon
import com.uvg.lab5_pokeapi.ui.theme.PokedexBlue
import com.uvg.lab5_pokeapi.ui.theme.PokedexYellow

@Composable
fun PokemonDetailScreen(
    pokemon: Pokemon,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    //listado de imagenes
    val imageTypes = listOf(
        stringResource(R.string.pokemon_image_front) to pokemon.imageFront,
        stringResource(R.string.pokemon_image_back) to pokemon.imageBack,
        stringResource(R.string.pokemon_image_shiny_front) to pokemon.imageShinyFront,
        stringResource(R.string.pokemon_image_shiny_back) to pokemon.imageShinyBack
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(contentPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = pokemon.name.replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.headlineMedium,
            color = PokedexYellow,
            modifier = Modifier.padding(16.dp)
        )
        
        Text(
            text = "ID: ${pokemon.id}",
            style = MaterialTheme.typography.bodyLarge,
            color = PokedexYellow,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Adaptive(150.dp),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(imageTypes) { (type, imageUrl) ->
                PokemonImageCard(
                    imageUrl = imageUrl,
                    imageType = type,
                    pokemonName = pokemon.name
                )
            }
        }
    }
}

//composable de imagen del pokemon
@Composable
fun PokemonImageCard(
    imageUrl: String,
    imageType: String,
    pokemonName: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.aspectRatio(1f),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = PokedexBlue
        )
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            //carga imagen
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "$imageType of $pokemonName",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentScale = ContentScale.Fit,
                placeholder = painterResource(R.drawable.loading_img),
                error = painterResource(R.drawable.ic_broken_image)
            )
            Text(
                text = imageType,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = Color.Black, 
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


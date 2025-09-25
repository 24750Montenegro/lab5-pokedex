@file:OptIn(ExperimentalMaterial3Api::class)

package com.uvg.lab5_pokeapi.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.uvg.lab5_pokeapi.R
import com.uvg.lab5_pokeapi.navigation.Screen
import com.uvg.lab5_pokeapi.network.Pokemon
import com.uvg.lab5_pokeapi.ui.screens.HomeScreen
import com.uvg.lab5_pokeapi.ui.screens.PokemonDetailScreen
import com.uvg.lab5_pokeapi.ui.screens.PokemonViewModel
import com.uvg.lab5_pokeapi.ui.theme.PokedexYellow
import com.uvg.lab5_pokeapi.ui.theme.PokedexRed
import com.uvg.lab5_pokeapi.ui.theme.PokedexDarkRed

@Composable
fun PokeApp(navController: NavHostController = rememberNavController()) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { 
            PokeTopAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                scrollBehavior = scrollBehavior 
            ) 
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = PokedexDarkRed
        ) {
            val pokemonViewModel: PokemonViewModel = viewModel()
            
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route
            ) {
                composable(Screen.Home.route) {
                    HomeScreen(
                        pokeUiState = pokemonViewModel.pokeUiState,
                        onPokemonClick = { pokemon ->
                            navController.navigate(
                                Screen.PokemonDetail.createRoute(pokemon.id, pokemon.name)
                            )
                        },
                        contentPadding = paddingValues
                    )
                }
                
                composable(Screen.PokemonDetail.route) { backStackEntry ->
                    val pokemonId = backStackEntry.arguments?.getString("pokemonId")?.toIntOrNull() ?: 0
                    val pokemonName = backStackEntry.arguments?.getString("pokemonName") ?: ""
                    val pokemon = Pokemon(
                        name = pokemonName,
                        url = "https://pokeapi.co/api/v2/pokemon/$pokemonId/"
                    )
                    
                    PokemonDetailScreen(
                        pokemon = pokemon,
                        contentPadding = paddingValues
                    )
                }
            }
        }
    }
}

@Composable
fun PokeTopAppBar(
    currentScreen: String?,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior, 
    modifier: Modifier = Modifier
) {
    val title = when {
        currentScreen?.startsWith("pokemon_detail") == true -> stringResource(R.string.pokemon_details)
        else -> stringResource(R.string.app_name)
    }

    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                color = PokedexYellow
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = PokedexRed
        ),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        modifier = modifier
    )
}
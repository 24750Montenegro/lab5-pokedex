package com.uvg.lab5_pokeapi.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object PokemonDetail : Screen("pokemon_detail/{pokemonId}/{pokemonName}") {
        fun createRoute(pokemonId: Int, pokemonName: String) = "pokemon_detail/$pokemonId/$pokemonName"
    }
}
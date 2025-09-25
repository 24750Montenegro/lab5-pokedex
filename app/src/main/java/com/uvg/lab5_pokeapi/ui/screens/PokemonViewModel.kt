package com.uvg.lab5_pokeapi.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvg.lab5_pokeapi.network.PokeApi
import com.uvg.lab5_pokeapi.network.Pokemon
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface PokeUiState {
    data class Success(val pokemonList: List<Pokemon>) : PokeUiState
    object Error : PokeUiState
    object Loading : PokeUiState
}

class PokemonViewModel : ViewModel() {
    var pokeUiState: PokeUiState by mutableStateOf(PokeUiState.Loading)
        private set

    init {
        getPokemon()
    }

    fun getPokemon() {
        viewModelScope.launch {
            pokeUiState = PokeUiState.Loading
            pokeUiState = try {
                val response = PokeApi.retrofitService.getPokemon(100)
                PokeUiState.Success(response.results)
            } catch (e: IOException) {
                PokeUiState.Error
            } catch (e: HttpException) {
                PokeUiState.Error
            }
        }
    }
}
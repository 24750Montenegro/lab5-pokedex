package com.uvg.lab5_pokeapi.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvg.lab5_pokeapi.data.repository.MainRepository
import com.uvg.lab5_pokeapi.data.repository.Result
import com.uvg.lab5_pokeapi.network.Pokemon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Estados posibles de la UI
sealed interface PokeUiState {
    data class Success(val pokemonList: List<Pokemon>) : PokeUiState
    object Error : PokeUiState
    object Loading : PokeUiState
}

// ViewModel principal con StateFlow
class MainViewModel(
    private val repository: MainRepository = MainRepository()
) : ViewModel() {
    
    // StateFlow privado para manejar el estado
    private val _uiState = MutableStateFlow<PokeUiState>(PokeUiState.Loading)
    val uiState: StateFlow<PokeUiState> = _uiState.asStateFlow()

    init {
        getPokemon()
    }

    //obtener pokemones
    fun getPokemon() {
        viewModelScope.launch {
            // Observar cambios del repositorio
            repository.getPokemonList(100).collect { result ->
                // Actualizar StateFlow según el resultado
                _uiState.value = when (result) {
                    is Result.Loading -> PokeUiState.Loading
                    is Result.Success -> PokeUiState.Success(result.data)
                    is Result.Error -> PokeUiState.Error
                }
            }
        }
    }
}
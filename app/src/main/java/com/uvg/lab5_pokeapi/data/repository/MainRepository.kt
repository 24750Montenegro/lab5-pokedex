package com.uvg.lab5_pokeapi.data.repository

import com.uvg.lab5_pokeapi.data.remote.PokeApi
import com.uvg.lab5_pokeapi.network.Pokemon
import com.uvg.lab5_pokeapi.network.PokeApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

// Repositorio para manejar datos de la API
class MainRepository {
    
    // Obtener lista de pokemones usando Flow
    suspend fun getPokemonList(limit: Int = 100): Flow<Result<List<Pokemon>>> = flow {
        try {
            emit(Result.Loading)
            // Llamada a la API
            val response = PokeApi.retrofitService.getPokemon(limit)
            emit(Result.Success(response.results))
        } catch (e: IOException) {
            emit(Result.Error("Network error: ${e.message}"))
        } catch (e: HttpException) {
            emit(Result.Error("HTTP error: ${e.message}"))
        } catch (e: Exception) {
            emit(Result.Error("Unknown error: ${e.message}"))
        }
    }
}

// Estados posibles del resultado
sealed class Result<out T> {
    object Loading : Result<Nothing>()
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
}
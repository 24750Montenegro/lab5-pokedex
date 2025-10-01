package com.uvg.lab5_pokeapi.data.remote

import com.uvg.lab5_pokeapi.network.PokeApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

// Interface para definir endpoints de la API
interface ApiService {
    // Obtener lista de pokemones
    @GET("pokemon")
    suspend fun getPokemon(@Query("limit") limit: Int = 100): PokeApiResponse
}

object PokeApi {
    val retrofitService: ApiService by lazy {
        RetrofitClient.instance.create(ApiService::class.java)
    }
}
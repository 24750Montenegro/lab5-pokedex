package com.uvg.lab5_pokeapi.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val  BASE_URL = "https://pokeapi.co/api/v2/"

// instancia de retrofit
private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()


interface PokeApiService{
    // obtener la lista de pokemones
    @GET("pokemon")
    //limitar la consulta de pokemones a 100
    suspend fun getPokemon(@Query("limit") limit: Int = 100): PokeApiResponse
}

object PokeApi{
    val retrofitService: PokeApiService by lazy {
        retrofit.create(PokeApiService::class.java)
    }
}
package com.uvg.lab5_pokeapi.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Cliente Retrofit 
object RetrofitClient {
    private const val BASE_URL = "https://pokeapi.co/api/v2/"
    
    // Instancia de Retrofit
    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }
}
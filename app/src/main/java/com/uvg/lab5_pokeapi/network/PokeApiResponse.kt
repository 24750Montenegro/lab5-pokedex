package com.uvg.lab5_pokeapi.network

import com.google.gson.annotations.SerializedName

data class PokeApiResponse(
    @SerializedName("results") val results: List<Pokemon>
){
    @Suppress("unused")
    constructor(): this(emptyList())
}

data class Pokemon(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
){
    @Suppress("unused")
    constructor(): this("", "")

    val id: Int
        get() {
            return try {
                val segments = url.trimEnd('/').split('/')
                // Obtener el id del ultimo segmento de la url
                segments.last().toIntOrNull() ?: 0
            } catch (e: Exception) {
                0
            }
        }

        //obtención de las imagenes
    val imageUrl: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"

    val imageFront: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
    
    val imageBack: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/$id.png"

    val imageShinyFront: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/$id.png"
    
    val imageShinyBack: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/$id.png"

    val allImages: List<String>
        get() = listOf(imageUrl, imageFront, imageBack, imageShinyFront, imageShinyBack)
}
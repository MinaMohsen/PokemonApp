package com.example.pokemonapp.data

import com.example.pokemonapp.data.model.PokemonData
import com.example.pokemonapp.data.model.PokemonList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {
    @GET("pokemon/")
    suspend fun getPokemonList(
        @Query("limit") limit: Int?,
        @Query("offset") offset: Int?
    ): PokemonList

    @GET("pokemon/{id}/")
    suspend fun getPokemonInfo(
        @Path("id") id: Int
    ): PokemonData
}
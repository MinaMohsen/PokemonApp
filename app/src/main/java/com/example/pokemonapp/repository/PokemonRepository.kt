package com.example.pokemonapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.pokemonapp.data.PokemonApi
import com.example.pokemonapp.data.datasource.PokemonDataSource
import com.example.pokemonapp.utils.Constants.PAGE_SIZE
import javax.inject.Inject

class PokemonRepository @Inject constructor(private val pokemonApi: PokemonApi) : BaseRepository() {

    fun getPokemon(searchString: String?) = Pager(
        config = PagingConfig(enablePlaceholders = false, pageSize = PAGE_SIZE),
        pagingSourceFactory = {
            PokemonDataSource(pokemonApi, searchString)
        }
    ).flow

    suspend fun getPokemonInfo(id: Int) = pokemonApi.getPokemonInfo(id)
}
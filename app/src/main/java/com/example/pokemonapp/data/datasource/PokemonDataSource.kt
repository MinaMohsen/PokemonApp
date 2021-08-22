package com.example.pokemonapp.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pokemonapp.data.PokemonApi
import com.example.pokemonapp.data.model.Pokemon
import com.example.pokemonapp.utils.Constants.SEARCH_LOAD_SIZE
import com.example.pokemonapp.utils.Constants.STARTING_OFFSET_INDEX
import java.io.IOException

class PokemonDataSource(
    private val pokemonApi: PokemonApi,
    private val searchString: String?
) : PagingSource<Int, Pokemon>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        val offset = params.key ?: STARTING_OFFSET_INDEX
        val loadSize = if (searchString == null) params.loadSize else SEARCH_LOAD_SIZE
        return try {
            val data = pokemonApi.getPokemonList(loadSize, offset)
            val filteredData = if (searchString != null) {
                data.results.filter { it.name.contains(searchString, true) }
            } else {
                data.results
            }
            LoadResult.Page(
                data = filteredData,
                prevKey = if (offset == STARTING_OFFSET_INDEX) null else offset - loadSize,
                nextKey = if (data.next == null) null else offset + loadSize
            )
        } catch (t: Throwable) {
            var exception = t
            if (t is IOException) {
                exception = IOException("Please check internet connection")
            }
            LoadResult.Error(exception)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, Pokemon>) = state.anchorPosition
}
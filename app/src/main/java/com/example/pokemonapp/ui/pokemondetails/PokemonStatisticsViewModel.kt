package com.example.pokemonapp.ui.pokemondetails

import androidx.lifecycle.ViewModel
import com.example.pokemonapp.data.model.PokemonData
import com.example.pokemonapp.repository.PokemonRepository
import com.example.pokemonapp.utils.NetworkResource
import com.example.pokemonapp.utils.extractId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class PokemonStatisticsViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    suspend fun getPokemonInfo(url: String) = flow {
        val id = url.extractId()
        emit(NetworkResource.Loading)
        emit(NetworkResource.Success(pokemonRepository.getPokemonInfo(id)))
    }

}

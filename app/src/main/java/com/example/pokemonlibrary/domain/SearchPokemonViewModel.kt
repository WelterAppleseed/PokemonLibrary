package com.example.pokemonlibrary.domain

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import com.example.pokemonlibrary.repository.AppRepository
import androidx.lifecycle.LiveData

import com.example.pokemonlibrary.repository.database.entity.PokemonEntity
import com.example.pokemonlibrary.single_live_data.SingleLiveEvent


class SearchPokemonViewModel(application: Application, private val repository: AppRepository): BaseViewModel(application)  {
    private val livePokemonData = SingleLiveEvent<List<PokemonEntity>>()

    @SuppressLint("CheckResult")
    fun getAllPokemon() {
        repository.getAllPokemons()?.subscribe {list -> livePokemonData.value = list}
    }

    fun getLiveDataPokemon(): LiveData<List<PokemonEntity>> {
        return livePokemonData
    }

    fun update(pokemonEntity: PokemonEntity) {
        repository.update(pokemonEntity)
    }
    fun getAllFavoritesPokemon(): MutableList<PokemonEntity> {
        return repository.getFavorites()
    }
}
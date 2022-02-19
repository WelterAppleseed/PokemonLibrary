package com.example.pokemonlibrary.domain

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.LiveData
import com.example.pokemonlibrary.repository.AppRepository
import com.example.pokemonlibrary.repository.database.entity.PokemonEntity
import com.example.pokemonlibrary.single_live_data.SingleLiveEvent


class RepositoryPokemonViewModel(application: Application, private val repository: AppRepository): BaseViewModel(application)  {
    private val livePokemonListData = SingleLiveEvent<List<PokemonEntity>>()
    private val livePokemonData = SingleLiveEvent<PokemonEntity>()

    @SuppressLint("CheckResult")
    fun getAllPokemon(isOnline: Boolean) {
        repository.getAllPokemons(isOnline)?.subscribe {list -> livePokemonListData.value = list}
    }

    fun getLiveDataPokemon(): LiveData<List<PokemonEntity>> {
        return livePokemonListData
    }

    fun update(pokemonEntity: PokemonEntity) {
        repository.update(pokemonEntity)
    }

    fun getAllFavoritesPokemon(): MutableList<PokemonEntity> {
        return repository.getFavorites()
    }


    @SuppressLint("CheckResult")
    fun getPokemon(name: String, isOnline: Boolean) {
        repository.getPokemon(name, isOnline).subscribe { pokemon -> livePokemonData.value = pokemon}
    }

    fun getLiveData(): LiveData<PokemonEntity> {
        return livePokemonData
    }
}
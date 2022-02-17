package com.example.pokemonlibrary.domain

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.pokemonlibrary.repository.AppRepository
import com.example.pokemonlibrary.repository.database.entity.PokemonEntity
import com.example.pokemonlibrary.single_live_data.SingleLiveEvent

class RandomPokemonViewModel(application: Application, private val repository: AppRepository): BaseViewModel(application)  {
    private val livePokemonData = SingleLiveEvent<PokemonEntity>()

    @SuppressLint("CheckResult")
    fun getPokemon(name: String) {
        repository.getPokemon(name).subscribe { pokemon -> livePokemonData.value = pokemon}
    }

    fun getLiveData(): LiveData<PokemonEntity> {
        return livePokemonData
    }
    fun update(pokemonEntity: PokemonEntity) {
        repository.update(pokemonEntity)
    }
}
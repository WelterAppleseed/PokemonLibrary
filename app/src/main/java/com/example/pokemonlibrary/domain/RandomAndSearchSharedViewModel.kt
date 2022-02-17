package com.example.pokemonlibrary.domain

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokemonlibrary.repository.database.entity.PokemonEntity

class RandomAndSearchSharedViewModel: ViewModel() {
    val bundleFromRandomToSearch = MutableLiveData<List<PokemonEntity>>()
    val bundleFromSearchToRandom = MutableLiveData<List<PokemonEntity>>()

}
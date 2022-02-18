package com.example.pokemonlibrary.domain

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokemonlibrary.repository.database.entity.PokemonEntity

class SearchRecyclerViewModel: ViewModel() {
    val bundleFromSearch = MutableLiveData<MutableList<PokemonEntity>>()
    val bundleToSearch = MutableLiveData<MutableList<PokemonEntity>>()

}
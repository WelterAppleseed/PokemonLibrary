package com.example.pokemonlibrary.domain

import android.app.Application
import android.content.Context
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import com.example.pokemonlibrary.PokemonLibraryApp

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {
    fun getContext(): Context = getApplication<PokemonLibraryApp>()
    fun getString(@StringRes id: Long): String = getContext().getString((id.toInt()))
}
package com.example.pokemonlibrary.di.module

import com.example.pokemonlibrary.PokemonLibraryApp
import com.example.pokemonlibrary.repository.AppRepository
import com.example.pokemonlibrary.di.scope.ViewModelScope
import com.example.pokemonlibrary.domain.SearchPokemonViewModel
import com.example.pokemonlibrary.domain.RandomPokemonViewModel
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule(private val app: PokemonLibraryApp) {

    @ViewModelScope
    @Provides
    fun provideAllPokemonViewModel(repository: AppRepository): SearchPokemonViewModel {
        return SearchPokemonViewModel(app, repository)
    }
    @ViewModelScope
    @Provides
    fun provideSinglePokemonViewModel(repository: AppRepository): RandomPokemonViewModel {
        return RandomPokemonViewModel(app, repository)
    }
}
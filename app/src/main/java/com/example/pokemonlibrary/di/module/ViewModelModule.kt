package com.example.pokemonlibrary.di.module

import com.example.pokemonlibrary.PokemonLibraryApp
import com.example.pokemonlibrary.repository.AppRepository
import com.example.pokemonlibrary.di.scope.ViewModelScope
import com.example.pokemonlibrary.domain.RepositoryPokemonViewModel
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule(private val app: PokemonLibraryApp) {

    @ViewModelScope
    @Provides
    fun provideAllPokemonViewModel(repository: AppRepository): RepositoryPokemonViewModel {
        return RepositoryPokemonViewModel(app, repository)
    }
}
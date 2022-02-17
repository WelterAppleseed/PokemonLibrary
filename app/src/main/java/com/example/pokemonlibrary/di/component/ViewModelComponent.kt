package com.example.pokemonlibrary.di.component

import com.example.pokemonlibrary.presentation.MainActivity
import com.example.pokemonlibrary.di.module.ViewModelModule
import com.example.pokemonlibrary.di.scope.ViewModelScope
import com.example.pokemonlibrary.presentation.fragments.*
import dagger.Component

@ViewModelScope
@Component(modules = [ViewModelModule::class], dependencies = [RepositoryComponent::class])
interface ViewModelComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: SearchedPokemonsFragment)
    fun inject(fragment: SinglePokemonFragment)
    fun inject(fragment: LaunchFragment)
    fun inject(fragment: FavoritePokemonsFragment)
    fun inject(fragment: RandomPokemonsFragment)
}
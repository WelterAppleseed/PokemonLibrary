package com.example.pokemonlibrary.di.component


import com.example.pokemonlibrary.di.module.RepositoryModule
import com.example.pokemonlibrary.repository.AppRepository
import com.example.pokemonlibrary.di.scope.RepositoryScope
import dagger.Component

@RepositoryScope
@Component(modules = [RepositoryModule::class], dependencies = [ApiComponent::class, DatabaseComponent::class])
interface RepositoryComponent {
    val repository: AppRepository
}
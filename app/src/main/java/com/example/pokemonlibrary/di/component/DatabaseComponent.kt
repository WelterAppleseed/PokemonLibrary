package com.example.pokemonlibrary.di.component

import com.example.pokemonlibrary.di.module.DatabaseModule
import com.example.pokemonlibrary.repository.database.AppDatabase
import com.example.pokemonlibrary.di.scope.ApiScope
import dagger.Component

@Component(modules = [DatabaseModule::class])
interface DatabaseComponent {
    val pokemonDatabase: AppDatabase
}
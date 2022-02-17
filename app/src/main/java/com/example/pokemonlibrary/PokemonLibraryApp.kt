package com.example.pokemonlibrary

import android.app.Application
import androidx.room.Room
import com.example.pokemonlibrary.di.component.*
import com.example.pokemonlibrary.di.module.ApiModule
import com.example.pokemonlibrary.di.module.DatabaseModule
import com.example.pokemonlibrary.di.module.RepositoryModule
import com.example.pokemonlibrary.di.module.ViewModelModule
import com.example.pokemonlibrary.repository.database.AppDatabase

class PokemonLibraryApp : Application() {
    private var viewModelComponent: ViewModelComponent? = null
    private var database: AppDatabase? = null

    override fun onCreate() {
        super.onCreate()
        initPokemonDatabase()
        initDagger()
    }
    private fun initPokemonDatabase() {
        database = Room.databaseBuilder(this.applicationContext, AppDatabase::class.java, "pokemons")
            .allowMainThreadQueries()
            .build()
    }
    private fun initDagger() {
        val apiComponent = DaggerApiComponent.builder()
            .apiModule(ApiModule())
            .build()
        val databaseComponent = DaggerDatabaseComponent.builder()
            .databaseModule(DatabaseModule(this.database!!))
            .build()
        val repositoryComponent = DaggerRepositoryComponent.builder()
            .apiComponent(apiComponent)
            .databaseComponent(databaseComponent)
            .repositoryModule(RepositoryModule())
            .build()
        viewModelComponent = DaggerViewModelComponent.builder()
            .repositoryComponent(repositoryComponent)
            .viewModelModule(ViewModelModule(this))
            .build()
    }
    fun getViewModelComponent(): ViewModelComponent {
        return this.viewModelComponent!!
    }
}
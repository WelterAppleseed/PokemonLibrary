package com.example.pokemonlibrary.di.module

import com.example.pokemonlibrary.repository.database.AppDatabase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule(private val appDatabase: AppDatabase) {
    @Provides
    fun providesRoomDatabase(): AppDatabase {
        return appDatabase
    }
}
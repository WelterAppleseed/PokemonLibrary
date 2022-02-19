package com.example.pokemonlibrary.di.module

import com.example.pokemonlibrary.di.scope.RepositoryScope
import com.example.pokemonlibrary.repository.AppRepository
import com.example.pokemonlibrary.repository.database.AppDatabase
import com.example.pokemonlibrary.repository.server.ServerCommunicator
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @RepositoryScope
    @Provides
    fun providesRepository(communicator: ServerCommunicator, database: AppDatabase): AppRepository {
        return AppRepository(communicator, database)
    }
}
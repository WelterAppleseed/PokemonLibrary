package com.example.pokemonlibrary.di.component

import com.example.pokemonlibrary.di.module.ApiModule
import com.example.pokemonlibrary.repository.server.ServerCommunicator
import com.example.pokemonlibrary.di.scope.ApiScope
import dagger.Component

@ApiScope
@Component(modules = [ApiModule::class])
interface ApiComponent {
    val serverCommunicator: ServerCommunicator
}
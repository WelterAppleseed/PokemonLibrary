package com.example.pokemonlibrary.repository.server

import com.example.pokemonlibrary.repository.database.entity.PokemonEntity
import com.example.pokemonlibrary.repository.database.pojo.ListOfPokemonUrls
import com.example.pokemonlibrary.repository.database.pojo.PokemonResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApiService {

    @GET("pokemon?limit=2000&offset=0")
    fun getAllPokemons(): Single<ListOfPokemonUrls>

    @GET("pokemon/{id}")
    fun getPokemonById(@Path(value = "id") id: Long): Single<PokemonEntity>

    @GET("pokemon/{name}")
    fun getPokemonByName(@Path(value = "name") name: String): Single<PokemonEntity>
}
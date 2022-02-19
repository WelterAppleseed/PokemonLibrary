package com.example.pokemonlibrary.repository.server

import com.example.pokemonlibrary.repository.database.entity.PokemonEntity
import com.example.pokemonlibrary.repository.database.pojo.ListOfPokemonUrls
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApiService {

    @GET("pokemon?limit=2000&offset=0")
    fun getAllPokemons(): Single<ListOfPokemonUrls>

    @GET("pokemon/{name}")
    fun getPokemonByName(@Path(value = "name") name: String): Single<PokemonEntity>
}
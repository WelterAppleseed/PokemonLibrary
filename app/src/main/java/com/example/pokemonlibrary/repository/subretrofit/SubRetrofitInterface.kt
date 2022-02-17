package com.example.pokemonlibrary.repository.subretrofit

import com.example.pokemonlibrary.repository.database.entity.PokemonEntity
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SubRetrofitInterface {
    @GET("pokemon/{name}")
    fun getPokemon(@Path(value = "name") name: String): Single<PokemonEntity>
}
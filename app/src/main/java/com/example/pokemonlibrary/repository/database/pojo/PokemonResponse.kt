package com.example.pokemonlibrary.repository.database.pojo

import com.example.pokemonlibrary.repository.database.entity.PokemonEntity
import com.example.pokemonlibrary.repository.database.entity.PokemonLink
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PokemonResponse {
    @SerializedName("count")
    @Expose
    var count: Int = 0
    @SerializedName("results")
    @Expose
    var pokemons: List<PokemonLink>? = null
}
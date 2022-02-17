package com.example.pokemonlibrary.repository.database.entity

import com.example.pokemonlibrary.repository.database.pojo.Sprites
import com.google.gson.annotations.SerializedName

data class PokemonEntit(
    @SerializedName("abilities")
    var abilities : List<Abilities>,
    @SerializedName("base_experience")
    var base_experience: Int,
    @SerializedName("height")
    var height: Int,
    @SerializedName("id")
    var id: Int,
    @SerializedName("moves")
    var moves: List<Moves>,
    @SerializedName("name")
    var name: String,
    @SerializedName("order")
    var order: Int,
    @SerializedName("past_types")
    var past_types: List<String>,
    @SerializedName("species")
    var species: Species,
    @SerializedName("sprites")
    var sprites: Sprites,
    @SerializedName("stats")
    var stats: List<Stats>,
    @SerializedName("types")
    var types: List<Types>,
    @SerializedName("weight")
    var weight: Int
)

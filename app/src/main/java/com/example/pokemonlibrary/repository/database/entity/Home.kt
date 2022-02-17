package com.example.pokemonlibrary.repository.database.entity

import com.google.gson.annotations.SerializedName

data class Home(
    @SerializedName("front_default")
    var frontDefault: String,
    @SerializedName("front_shiny")
    var front_shiny: String
)

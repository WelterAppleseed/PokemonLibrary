package com.example.pokemonlibrary.repository.database.pojo

import com.google.gson.annotations.SerializedName

data class Home(
    @SerializedName("front_default")
    var frontDefault: String,
    @SerializedName("front_shiny")
    var front_shiny: String
)

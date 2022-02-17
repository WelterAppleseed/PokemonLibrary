package com.example.pokemonlibrary.repository.database.entity

import com.google.gson.annotations.SerializedName

data class Species(
    @SerializedName("name")
    var name: String,
    @SerializedName("url")
    var url: String
)

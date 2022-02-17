package com.example.pokemonlibrary.repository.database.entity

import com.google.gson.annotations.SerializedName

data class Stat(
    @SerializedName("name")
    var name: String,
    @SerializedName("stat")
    var url: String
)

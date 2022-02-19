package com.example.pokemonlibrary.repository.database.pojo

import com.google.gson.annotations.SerializedName

data class Type(
    @SerializedName("name")
    var name: String,
    @SerializedName("url")
    var url: String
)

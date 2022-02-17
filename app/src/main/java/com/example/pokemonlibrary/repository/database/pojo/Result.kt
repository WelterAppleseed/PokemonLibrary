package com.example.pokemonlibrary.repository.database.pojo

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("name")
    var name: String,
    @SerializedName("url")
    var url: String
)

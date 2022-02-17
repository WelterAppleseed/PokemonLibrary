package com.example.pokemonlibrary.repository.database.entity

import com.google.gson.annotations.SerializedName

data class Types(
    @SerializedName("slot")
    var slot: Int,
    @SerializedName("type")
    var type: Type
)

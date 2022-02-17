package com.example.pokemonlibrary.repository.database.entity

import com.google.gson.annotations.SerializedName

data class OfficialArtwork(
    @SerializedName("front_default")
    var frontDefault: String
)

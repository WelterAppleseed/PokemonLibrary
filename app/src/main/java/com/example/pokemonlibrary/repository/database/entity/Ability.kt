package com.example.pokemonlibrary.repository.database.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Ability(
    @SerializedName("name")
    var abilityName: String,
    @SerializedName("url")
    var abilityDescription: String
)

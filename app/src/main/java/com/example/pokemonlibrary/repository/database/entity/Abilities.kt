package com.example.pokemonlibrary.repository.database.entity

import com.google.gson.annotations.SerializedName

data class Abilities(
    @SerializedName("ability")
    var ability: Ability,
    @SerializedName("is_hidden")
    var isHidden: Boolean,
)

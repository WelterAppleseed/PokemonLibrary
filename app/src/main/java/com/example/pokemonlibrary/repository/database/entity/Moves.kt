package com.example.pokemonlibrary.repository.database.entity

import com.google.gson.annotations.SerializedName

data class Moves(
    @SerializedName("move")
    var move: Move
)

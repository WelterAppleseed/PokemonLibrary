package com.example.pokemonlibrary.repository.database.pojo

import com.google.gson.annotations.SerializedName

data class Moves(
    @SerializedName("move")
    var move: Move
)

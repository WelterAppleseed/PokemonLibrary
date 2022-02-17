package com.example.pokemonlibrary.repository.database.pojo

import com.example.pokemonlibrary.repository.database.entity.Other
import com.google.gson.annotations.SerializedName

data class Sprites(
    @SerializedName("other")
    var other: Other
    )

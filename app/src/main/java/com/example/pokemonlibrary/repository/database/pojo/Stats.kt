package com.example.pokemonlibrary.repository.database.pojo

import com.google.gson.annotations.SerializedName

data class Stats(
    @SerializedName("base_stat")
    var baseStat: Int,
    @SerializedName("stat")
    var stat: Stat
)

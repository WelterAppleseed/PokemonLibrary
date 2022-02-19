package com.example.pokemonlibrary.repository.database.pojo

import com.google.gson.annotations.SerializedName


data class State(
    @SerializedName("base_stat")
    var name: String,
    @SerializedName("stat")
    var value: Stat
) {
}
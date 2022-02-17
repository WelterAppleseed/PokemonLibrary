package com.example.pokemonlibrary.repository.database.pojo

import com.google.gson.annotations.SerializedName

data class ListOfPokemonUrls(
    @SerializedName("count")
    var count: Int,
    @SerializedName("next")
    var next: String,
    @SerializedName("results")
    var results : List<Result>
) {
}
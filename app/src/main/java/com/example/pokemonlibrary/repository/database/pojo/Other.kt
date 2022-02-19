package com.example.pokemonlibrary.repository.database.pojo

import com.google.gson.annotations.SerializedName

data class Other(
    @SerializedName("dream_world")
    var dreamWorld: DreamWorld,
    @SerializedName("home")
    var home: Home,
    @SerializedName("official-artwork")
    var officialArtwork: OfficialArtwork
)
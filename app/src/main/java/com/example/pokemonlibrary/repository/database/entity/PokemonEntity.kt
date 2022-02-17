package com.example.pokemonlibrary.repository.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.pokemonlibrary.repository.database.pojo.Sprites
import com.example.pokemonlibrary.repository.database.type_converters.AbilityConverter
import com.example.pokemonlibrary.repository.database.type_converters.ListConverter
import com.example.pokemonlibrary.repository.database.type_converters.StateConverter
import com.google.gson.annotations.SerializedName

@Entity(tableName = "pokemons")
@TypeConverters(value =  [AbilityConverter::class, StateConverter::class, ListConverter::class])
data class PokemonEntity(
    @PrimaryKey
    @SerializedName("id")
    var id: Long,
    @SerializedName("abilities")
    var abilities : List<Abilities>,
    @SerializedName("base_experience")
    var base_experience: Int,
    @SerializedName("height")
    var height: Int,
    @SerializedName("moves")
    var moves: List<Moves>,
    @SerializedName("name")
    var name: String,
    @SerializedName("order")
    var order: Int,
    @SerializedName("species")
    var species: Species,
    @SerializedName("sprites")
    var sprites: Sprites,
    @SerializedName("stats")
    var stats: List<Stats>,
    @SerializedName("types")
    var types: List<Types>,
    @SerializedName("weight")
    var weight: Int,
    @SerializedName("is_favorite")
    var isFavorite: Boolean = false)

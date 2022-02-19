package com.example.pokemonlibrary.repository.database.type_converters

import androidx.room.TypeConverter
import com.example.pokemonlibrary.repository.database.pojo.Species
import com.example.pokemonlibrary.repository.database.pojo.Sprites
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object StateConverter {
    @TypeConverter
    @JvmStatic
    fun fromSprites(sprite: Sprites): String {
        return Gson().toJson(sprite)
    }

    @TypeConverter
    @JvmStatic
    fun toSprites(value: String): Sprites {
        val sprite: Sprites
        val malType = object : TypeToken<Sprites>() {}.type
        val list = Gson().fromJson<Sprites>(value, malType)
        sprite = Sprites(list.other)
        return sprite
    }
    @TypeConverter
    @JvmStatic
    fun fromSpecies(species: Species): String {
        return Gson().toJson(species)
    }
    @TypeConverter
    @JvmStatic
    fun toSpecies(value: String): Species {
        val species: Species
        val malType = object : TypeToken<Species>() {}.type
        val map = Gson().fromJson<Species>(value, malType)
        species = Species(map.name, map.url)
        return species
    }

}

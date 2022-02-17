package com.example.pokemonlibrary.repository.database.type_converters

import androidx.room.TypeConverter
import com.example.pokemonlibrary.repository.database.entity.Species
import com.example.pokemonlibrary.repository.database.pojo.Sprites
import com.example.pokemonlibrary.repository.database.entity.Stat
import com.example.pokemonlibrary.repository.database.entity.State
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object StateConverter {
    @TypeConverter
    @JvmStatic
    fun fromStates(states: List<State>): String {
        val map = mutableMapOf<String, Stat>()
        states.forEach { map[it.name] = it.value }
        return Gson().toJson(map)
    }

    @TypeConverter
    @JvmStatic
    fun toStates(value: String): List<State> {
        val list = mutableListOf<State>()
        val malType = object : TypeToken<Map<String, Int>>() {}.type
        val map = Gson().fromJson<Map<String,Stat>>(value, malType)
        map.forEach { list.add(State(it.key, it.value)) }
        return list
    }
    @TypeConverter
    @JvmStatic
    fun fromSprites(sprite: Sprites): String {
        return Gson().toJson(sprite)
    }

    @TypeConverter
    @JvmStatic
    fun toSprites(value: String): Sprites {
        var sprite: Sprites
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
        var species: Species
        val malType = object : TypeToken<Species>() {}.type
        val map = Gson().fromJson<Species>(value, malType)
        species = Species(map.name, map.url)
        return species
    }

}

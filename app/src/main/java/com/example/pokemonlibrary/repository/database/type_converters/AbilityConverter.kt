package com.example.pokemonlibrary.repository.database.type_converters

import androidx.room.TypeConverter
import com.example.pokemonlibrary.repository.database.entity.*
import com.example.pokemonlibrary.repository.database.pojo.Sprites
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object AbilityConverter {
    @TypeConverter
    @JvmStatic
    fun fromAbilities(abilities: List<Abilities>): String {
        val map = mutableMapOf<String, Boolean>()
        abilities.forEach { map[fromAbility(it.ability)] = it.isHidden }
        return Gson().toJson(map)
    }

    @TypeConverter
    @JvmStatic
    fun toAbilities(value: String): List<Abilities> {
        val list = mutableListOf<Abilities>()
        val malType = object : TypeToken<Map<String, Boolean>>() {}.type
        val map = Gson().fromJson<Map<String, Boolean>>(value, malType)
        map.forEach {
            list.add(Abilities(toAbility(it.key), it.value)) }
        return list
    }
    @TypeConverter
    @JvmStatic
    fun fromMoves(moves: List<Moves>): String {
        val list = mutableListOf<Move>()
        moves.forEach { list.add(it.move) }
        return Gson().toJson(list)
    }
    @TypeConverter
    @JvmStatic
    fun toMoves(value: String): List<Moves> {
        val list = mutableListOf<Moves>()
        val malType = object : TypeToken<List<Move>>() {}.type
        val l = Gson().fromJson<List<Move>>(value, malType)
        l.forEach { list.add(Moves(it)) }
        return list
    }

    @TypeConverter
    @JvmStatic
    fun fromStats(stats: List<Stats>): String {
        val map = mutableMapOf<Int, Stat>()
        stats.forEach { map[it.baseStat] = it.stat }
        return Gson().toJson(map)
    }
    @TypeConverter
    @JvmStatic
    fun toStats(value: String): List<Stats> {
        val list = mutableListOf<Stats>()
        val malType = object : TypeToken<Map<Int, Stat>>() {}.type
        val map = Gson().fromJson<Map<Int, Stat>>(value, malType)
        map.forEach { list.add(Stats(it.key, it.value)) }
        return list
    }
    @TypeConverter
    @JvmStatic
    fun fromTypes(stats: List<Types>): String {
        val map = mutableMapOf<Int, Type>()
        stats.forEach { map[it.slot] = it.type }
        return Gson().toJson(map)
    }
    @TypeConverter
    @JvmStatic
    fun toTypes(value: String): List<Types> {
        val list = mutableListOf<Types>()
        val malType = object : TypeToken<Map<Int, Type>>() {}.type
        val map = Gson().fromJson<Map<Int, Type>>(value, malType)
        map.forEach { list.add(Types(it.key, it.value)) }
        return list
    }
    @TypeConverter
    @JvmStatic
    fun fromAbility(ability: Ability): String {
        return Gson().toJson(ability)
    }

    @TypeConverter
    @JvmStatic
    fun toAbility(value: String): Ability {
        var ab: Ability
        val malType = object : TypeToken<Ability>() {}.type
        val list = Gson().fromJson<Ability>(value, malType)
        ab = Ability(list.abilityName, list.abilityDescription)
        return ab
    }

}

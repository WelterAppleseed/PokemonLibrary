package com.example.pokemonlibrary.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pokemonlibrary.repository.database.dao.PokemonDao
import com.example.pokemonlibrary.repository.database.entity.PokemonEntity
import com.example.pokemonlibrary.repository.database.type_converters.AbilityConverter
import com.example.pokemonlibrary.repository.database.type_converters.StateConverter

@Database(entities = [PokemonEntity::class], version = 1)
@TypeConverters(value =  [AbilityConverter::class, StateConverter::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun allPokemonDao(): PokemonDao
}
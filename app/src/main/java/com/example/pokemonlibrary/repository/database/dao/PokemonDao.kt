package com.example.pokemonlibrary.repository.database.dao

import androidx.room.*
import com.example.pokemonlibrary.repository.database.entity.PokemonEntity

@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokemons")
    fun getAll() : List<PokemonEntity>

    @Query("SELECT * FROM pokemons WHERE id=:id")
    fun getPokemon(id: Long): PokemonEntity

    @Query("SELECT * FROM pokemons WHERE name=:name")
    fun getPokemon(name: String): PokemonEntity

    @Query("SELECT * FROM pokemons WHERE isFavorite=:isFav")
    fun getFavoritePokemons(isFav: Boolean): List<PokemonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pokemon: PokemonEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pokemons: List<PokemonEntity>)

    @Update
    fun updateAll(pokemons: List<PokemonEntity>)

    @Update
    fun update(pokemon: PokemonEntity)

    @Update
    fun update(pookemons: List<PokemonEntity>)


    @Delete
    fun delete(pokemon: PokemonEntity)
}
package com.example.pokemonlibrary.presentation.interfaces

import com.example.pokemonlibrary.repository.database.entity.PokemonEntity

interface OnAddToFavoriteClickListener {
    fun update(pokemon: PokemonEntity)
    fun add(pokemon: PokemonEntity)
    fun delete(pokemon: PokemonEntity)
}
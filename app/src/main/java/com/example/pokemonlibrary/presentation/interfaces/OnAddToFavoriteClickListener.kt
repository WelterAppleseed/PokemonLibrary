package com.example.pokemonlibrary.presentation.interfaces

import com.example.pokemonlibrary.repository.database.entity.PokemonEntity

interface OnAddToFavoriteClickListener {
    fun addToFav(pokemon: PokemonEntity)
    fun deleteFromFav(pokemon: PokemonEntity)
}
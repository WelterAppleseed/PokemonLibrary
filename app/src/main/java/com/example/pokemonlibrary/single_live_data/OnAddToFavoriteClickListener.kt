package com.example.pokemonlibrary.single_live_data

import com.example.pokemonlibrary.repository.database.entity.PokemonEntity

interface OnAddToFavoriteClickListener {
    fun addToFav(pokemon: PokemonEntity)
    fun deleteFromFav(pokemon: PokemonEntity)
}
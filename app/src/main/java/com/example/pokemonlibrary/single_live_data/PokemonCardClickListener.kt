package com.example.pokemonlibrary.single_live_data

import com.example.pokemonlibrary.repository.database.entity.PokemonEntity

interface PokemonCardClickListener {
    fun openDetail(entity: PokemonEntity)
}
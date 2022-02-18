package com.example.pokemonlibrary.presentation.interfaces

import com.example.pokemonlibrary.repository.database.entity.PokemonEntity

interface PokemonCardClickListener {
    fun openDetail(entity: PokemonEntity)
}
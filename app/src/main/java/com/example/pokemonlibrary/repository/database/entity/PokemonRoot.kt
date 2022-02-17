package com.example.pokemonlibrary.repository.database.entity

class PokemonRoot {
    var count = 0
    var next: String? = null
    var previous: Any? = null
    var results: ArrayList<PokemonLink>? = null
}
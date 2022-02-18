package com.example.pokemonlibrary

import android.content.SharedPreferences
import android.widget.ImageButton
import com.example.pokemonlibrary.repository.database.entity.PokemonEntity

fun String.setNormalCharCases(): String {
    val builder = StringBuilder(this)
    while (builder.contains("-")) {
        builder.replace(builder.indexOf("-", 0, false)+1, builder.indexOf("-", 0, false)+2, builder[builder.indexOf("-", 0, false)+1].uppercase())
        builder.replace(builder.indexOf("-", 0, false), builder.indexOf("-", 0, false) + 1, " ")
    }
    return this.uppercase()[0] + builder.substring(1)
}
fun ImageButton.setState(isEnabled: Boolean) {
    this.isClickable = isEnabled
}
fun SharedPreferences.insert(name: String, item: String) {
    this.edit().putString(name, item).apply()
}
fun MutableList<PokemonEntity>.replaceByName(newPokemon: PokemonEntity) {
    for (pokemon in this) {
        if (pokemon.name == newPokemon.name) {
            this[this.indexOf(pokemon)] = newPokemon
        }
    }
}
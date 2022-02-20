package com.example.pokemonlibrary

import android.app.Activity
import android.content.SharedPreferences
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonlibrary.repository.database.entity.PokemonEntity

fun String.setNormalCharCases(): String {
    val builder = StringBuilder(this)
    while (builder.contains("-")) {
        builder.replace(
            builder.indexOf("-", 0, false) + 1,
            builder.indexOf("-", 0, false) + 2,
            builder[builder.indexOf("-", 0, false) + 1].uppercase()
        )
        builder.replace(builder.indexOf("-", 0, false), builder.indexOf("-", 0, false) + 1, " ")
    }
    return this.uppercase()[0] + builder.substring(1)
}

fun ImageButton.setState(isEnabled: Boolean) {
    this.isClickable = isEnabled
}

fun SharedPreferences.insertString(name: String, item: String) {
    this.edit().putString(name, item).apply()
}
fun SharedPreferences.removeString(name: String) {
    this.edit().remove(name).apply()
}
fun SharedPreferences.getString(name: String): String {
    return this.getString(name, "")!!
}
fun SharedPreferences.insertInt(name: String, position: Int) {
    this.edit().putInt(name, position).apply()
}
fun SharedPreferences.getInt(name: String): Int {
    return this.getInt(name, 0)
}

fun RecyclerView.setScrollPositionSavedListener(name: String, prefs: SharedPreferences) {
    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            prefs.insertInt(name, (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition())
        }
    })
    this.scrollToPosition(prefs.getInt((name)))
}

fun MutableList<PokemonEntity>.replaceByName(newPokemon: PokemonEntity) {
    for (pokemon in this) {
        if (pokemon.name == newPokemon.name) {
            this[this.indexOf(pokemon)] = newPokemon
        }
    }
}
fun Activity.getNoConnectionDialog(withClose: Boolean): AlertDialog {
    val message = if (withClose) {
        "Please, check your internet connection to load list of Pokemons!"
    } else {
        "There is no internet connection, that's why only text description is available for Pokemons instead of loaded earlier"
    }
    return AlertDialog.Builder(this)
        .setIcon(android.R.drawable.ic_dialog_alert)
        .setTitle("Internet Connection Alert")
        .setCancelable(false)
        .setMessage(message)
        .setPositiveButton(
            "Close"
        ) { dialog, _ ->
            if (withClose) {
                this.finish()
            } else {
                dialog.dismiss()
            }
        }.create()
}
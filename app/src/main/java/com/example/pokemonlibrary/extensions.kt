package com.example.pokemonlibrary

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
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

@RequiresApi(Build.VERSION_CODES.M)
fun isOnline(context: Context): kotlin.Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    if (capabilities != null) {
        when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            }
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            }
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
    }
    return false
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
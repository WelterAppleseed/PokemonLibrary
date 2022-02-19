package com.example.pokemonlibrary.presentation.fragments

import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.example.pokemonlibrary.R
import com.example.pokemonlibrary.repository.database.entity.PokemonEntity
import com.example.pokemonlibrary.setNormalCharCases
import kotlinx.android.synthetic.main.random_card.view.*

abstract class PokemonCardBaseFragment: BaseFragment() {
    val builder = StringBuilder()

    fun initCard(view: View, pokemon: PokemonEntity) {
        Log.i("PokemonCardBaseFragment","Start init card")
        if (view.pokemon_sprite != null) {
            if (pokemon.id.toInt() != -1) {
                Log.i("PokemonCardBaseFragment","Start getting img")
                Glide.with(view.context)
                    .load(pokemon.sprites.other.officialArtwork.frontDefault)
                    .placeholder(R.drawable.noimg)
                    .into(view.pokemon_sprite)
                Log.i("PokemonCardBaseFragment","Image received")
                pokemon.types.forEach { builder.append("${it.type.name} ".setNormalCharCases()) }
                view.common_stat_tv.text = view.context.getString(
                    R.string.common_stats,
                    pokemon.id,
                    builder.toString(),
                    pokemon.weight,
                    pokemon.height
                )
                builder.clear()
                Log.i("PokemonCardBaseFragment","Setting attacks for TextViews")
                pokemon.abilities.forEach {
                    builder.append(it.ability.abilityName.setNormalCharCases())
                    if (pokemon.abilities.indexOf(it) != pokemon.abilities.size-1) {
                        builder.append("\n")
                    }
                }
                Log.i("PokemonCardBaseFragment","Setting attacks2 for TextViews")
                view.attacks_tv.text = builder
                builder.clear()
                Log.i("PokemonCardBaseFragment","Setting stats for TextViews")
                pokemon.stats.forEach {
                    builder.append("${it.stat.name.setNormalCharCases()}: ${it.baseStat}")
                    if (pokemon.stats.indexOf(it) != pokemon.stats.size - 1) {
                        builder.append("\n")
                    }
                }
                Log.i("PokemonCardBaseFragment","Setting stats2 for TextViews")
                view.stats_tv.text = builder
                builder.clear()
                Log.i("PokemonCardBaseFragment","Setting name for TextViews")
                println(pokemon.name)
                view.pokemon_name.text = pokemon.name.setNormalCharCases()
                Log.i("PokemonCardBaseFragment","Setting name2 for TextViews")
                view.pokemon_name.animate().alpha(1F).setDuration(100).start()
            }
        }
        Log.i("PokemonCardBaseFragment","Over initializing")
    }
}
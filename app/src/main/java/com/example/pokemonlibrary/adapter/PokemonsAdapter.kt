package com.example.pokemonlibrary.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonlibrary.R
import com.example.pokemonlibrary.repository.database.entity.PokemonEntity
import com.example.pokemonlibrary.setNormalCharCases
import com.example.pokemonlibrary.single_live_data.OnAddToFavoriteClickListener
import com.example.pokemonlibrary.single_live_data.PokemonCardClickListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_item.view.*
import java.util.*
import kotlin.collections.ArrayList

class PokemonsAdapter(
    private val pokemonList: List<PokemonEntity>,
    cardClickListener: PokemonCardClickListener,
    favoriteClickListener: OnAddToFavoriteClickListener
) : RecyclerView.Adapter<PokemonsAdapter.PokemonViewHolder>() {

    var nestedCardClickListener: PokemonCardClickListener = cardClickListener
    var nestedFavoriteClickListener: OnAddToFavoriteClickListener = favoriteClickListener
    var pokemonFilterList = listOf<PokemonEntity>()

    init {
        pokemonFilterList = pokemonList
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokemonsAdapter.PokemonViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return PokemonViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PokemonsAdapter.PokemonViewHolder, position: Int) {
        val pokemon = pokemonFilterList[position]
        holder.pokemonName.text = pokemonFilterList[position].name.setNormalCharCases()
        if (pokemon.isFavorite) {
            holder.pokemonFav.setImageResource(android.R.drawable.btn_star_big_on)
        } else {
            holder.pokemonFav.setImageResource(android.R.drawable.btn_star_big_off)
        }
        Picasso.get().load(pokemon.sprites.other.officialArtwork.frontDefault)
            .placeholder(R.drawable.noimg).into(holder.pokemonIcon)
        holder.bind(pokemon)
    }

    override fun getItemCount(): Int {
        return pokemonFilterList.size
    }

    inner class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pokemonName: TextView = itemView.pokemon_name
        var pokemonIcon: ImageView = itemView.pokemon_card_icon
        var pokemonFav: ImageView = itemView.fav_item_button

        fun bind(pokemon: PokemonEntity) {
            itemView.setOnClickListener {
                nestedCardClickListener.openDetail(pokemon)
            }
            pokemonFav.setOnClickListener {
                val isFav =  pokemon.isFavorite
                pokemon.isFavorite = !pokemon.isFavorite
                if (!isFav) {
                    nestedFavoriteClickListener.addToFav(pokemon)
                    (it as ImageView).setImageResource(android.R.drawable.btn_star_big_on)
                } else {
                    (it as ImageView).setImageResource(android.R.drawable.btn_star_big_off)
                    nestedFavoriteClickListener.deleteFromFav(pokemon)
                }
            }
        }

    }
    fun filter(substring: String) {
        val resultList = ArrayList<PokemonEntity>()
        for (row in pokemonList) {
            if (row.name.lowercase(Locale.ROOT).contains(substring.lowercase(Locale.ROOT))) {
                resultList.add(row)
            }
        }
        pokemonFilterList = resultList
        notifyDataSetChanged()
    }
}

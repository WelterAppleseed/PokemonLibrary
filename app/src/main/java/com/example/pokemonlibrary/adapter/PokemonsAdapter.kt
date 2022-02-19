package com.example.pokemonlibrary.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokemonlibrary.R
import com.example.pokemonlibrary.presentation.interfaces.OnAddToFavoriteClickListener
import com.example.pokemonlibrary.presentation.interfaces.PokemonCardClickListener
import com.example.pokemonlibrary.repository.database.entity.PokemonEntity
import com.example.pokemonlibrary.setNormalCharCases
import kotlinx.android.synthetic.main.card_item.view.*
import java.util.*
import kotlin.collections.ArrayList

class PokemonsAdapter(
    val context: Context,
    private val pokemonList: List<PokemonEntity>,
    cardClickListener: PokemonCardClickListener,
    favoriteClickListener: OnAddToFavoriteClickListener
) : RecyclerView.Adapter<PokemonsAdapter.PokemonViewHolder>() {

    var nestedCardClickListener: PokemonCardClickListener = cardClickListener
    var nestedFavoriteClickListener: OnAddToFavoriteClickListener = favoriteClickListener
    private var pokemonFilterList = listOf<PokemonEntity>()

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
            holder.pokemonFav.setImageResource(R.drawable.fav_on_dr)
        } else {
            holder.pokemonFav.setImageResource(R.drawable.fav_off_dr)
        }
        Glide.with(context)
            .load(pokemon.sprites.other.officialArtwork.frontDefault)
            .placeholder(R.drawable.noimg)
            .into(holder.pokemonIcon)
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
                    (it as ImageView).setImageResource(R.drawable.fav_on_dr)
                } else {
                    (it as ImageView).setImageResource(R.drawable.fav_off_dr)
                    nestedFavoriteClickListener.deleteFromFav(pokemon)
                }
            }
        }

    }
    @SuppressLint("NotifyDataSetChanged")
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

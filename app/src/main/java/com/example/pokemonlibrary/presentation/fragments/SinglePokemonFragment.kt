package com.example.pokemonlibrary.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.pokemonlibrary.PokemonLibraryApp
import com.example.pokemonlibrary.R
import com.example.pokemonlibrary.domain.RandomPokemonViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.pokemon_card.view.*
import javax.inject.Inject

class SinglePokemonFragment: PokemonCardBaseFragment() {

    var viewModel: RandomPokemonViewModel? = null
    @Inject set

    private var pokemonName = ""
    private var transitId = R.id.action_singlePokemonFragment_to_searchedPokemonsFragment
    private var transitFrom: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pokemonName = arguments?.getString(context?.getString(R.string.EXTRA_NAME_ID))!!
        if (arguments?.getInt("from_fav_frag")!! != 0) {
            transitId = arguments?.getInt("from_fav_frag")!!
            transitFrom =  arguments?.getInt("from")!!
        } else {
            R.id.action_singlePokemonFragment_to_searchedPokemonsFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val view = inflater.inflate(R.layout.pokemon_card, container, false)
        (activity as AppCompatActivity).fav_toolbar.setOnMenuItemClickListener(null)
       (view.context.applicationContext as PokemonLibraryApp).getViewModelComponent().inject(this)
        viewModel?.getPokemon(pokemonName.lowercase())
        viewModel?.getLiveData()?.observe(this, Observer {
            initCard(view, it)
        })
        view.back_button.setOnClickListener {
            val bundle = Bundle()
            if (transitFrom != 0) {
                bundle.putInt("from", transitFrom)
            }
            navigateTo(bundle, transitId)
            }
        return view
    }
}
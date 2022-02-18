package com.example.pokemonlibrary.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.example.pokemonlibrary.PokemonLibraryApp
import com.example.pokemonlibrary.R
import com.example.pokemonlibrary.domain.RepositoryPokemonViewModel
import com.example.pokemonlibrary.domain.SearchRecyclerViewModel
import com.example.pokemonlibrary.replaceByName
import com.example.pokemonlibrary.repository.database.entity.PokemonEntity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.pokemon_card.view.*
import javax.inject.Inject

class SinglePokemonFragment: PokemonCardBaseFragment() {

    var viewModel: RepositoryPokemonViewModel? = null
        @Inject set
    private lateinit var handleViewModel: SearchRecyclerViewModel
    private var pokemonName = ""
    private var transitId = R.id.action_singlePokemonFragment_to_searchedPokemonsFragment
    private var transitFrom: Int = 0
    private lateinit var pokemonList: MutableList<PokemonEntity>
    lateinit var pokemon: PokemonEntity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).fav_toolbar.setOnMenuItemClickListener(null)
        (activity as AppCompatActivity).fav_toolbar.menu.getItem(0).setIcon(R.drawable.fav_on_offed)
        pokemonName = arguments?.getString(context?.getString(R.string.EXTRA_NAME_ID))!!
        if (arguments?.getInt("from_fav_frag")!! != 0) {
            transitId = arguments?.getInt("from_fav_frag")!!
            transitFrom =  arguments?.getInt("from")!!
        }
        initBackClickCallback(arguments?.getInt("from")!!, transitId)
        handleViewModel = ViewModelProviders.of(requireActivity()).get(SearchRecyclerViewModel::class.java)
        handleViewModel.bundleFromSearch.observe(this, {
            pokemonList = it
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.pokemon_card, container, false)
        (view.context.applicationContext as PokemonLibraryApp).getViewModelComponent().inject(this)
        viewModel?.getPokemon(pokemonName)
        view.back_button.setOnClickListener {
            val bundle = Bundle()
            if (transitFrom != 0) {
                bundle.putInt("from", transitFrom)
            }
            pokemonList.replaceByName(pokemon)
            handleViewModel.bundleToSearch.observe(this, {
                pokemonList = it
            })
            navigateTo(bundle, transitId)
        }
        viewModel?.getLiveData()?.observe(this, {
            pokemon = it
            Log.i("SinglePokemonFragment", pokemon.toString())
            if (pokemon.isFavorite) {
                view.add_to_fav_single_button.background = ContextCompat.getDrawable(view.context, R.drawable.fav_on_dr)
            }
            initCard(view, it)
        })
        view.add_to_fav_single_button.setOnClickListener {
            if (!pokemon.isFavorite) {
                view.add_to_fav_single_button.background  = ContextCompat.getDrawable(view.context, R.drawable.fav_on_dr)
            } else {
                view.add_to_fav_single_button.background  = ContextCompat.getDrawable(view.context, R.drawable.fav_off_dr)
            }
            pokemon.isFavorite = !pokemon.isFavorite
            viewModel?.update(pokemon)
        }
        return view
    }
}
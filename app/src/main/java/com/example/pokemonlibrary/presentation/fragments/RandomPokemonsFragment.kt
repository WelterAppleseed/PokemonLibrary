package com.example.pokemonlibrary.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.example.pokemonlibrary.PokemonLibraryApp
import com.example.pokemonlibrary.R
import com.example.pokemonlibrary.domain.RepositoryPokemonViewModel
import com.example.pokemonlibrary.domain.SearchRecyclerViewModel
import com.example.pokemonlibrary.repository.database.entity.PokemonEntity
import com.example.pokemonlibrary.setState
import kotlinx.android.synthetic.main.card_item.view.*
import kotlinx.android.synthetic.main.fragment_searched_pokemons.view.*
import kotlinx.android.synthetic.main.pokemon_card.view.*
import kotlinx.android.synthetic.main.random_card.view.*
import javax.inject.Inject


class RandomPokemonsFragment : PokemonCardBaseFragment() {

    var randomViewModel: RepositoryPokemonViewModel? = null
        @Inject set
    private var pokemonList: List<PokemonEntity>? = null
    private lateinit var randomPokemon: PokemonEntity
    private lateinit var viewModelRandomAndSearch: SearchRecyclerViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initMenuClickListener(R.id.action_favoritePokemonsFragment_to_randomPokemonsFragment, R.id.action_randomPokemonsFragment_to_favoritePokemonsFragment)
        initBackClickCallback(null, R.id.action_randomPokemonsFragment_to_menuFragment)
        viewModelRandomAndSearch = ViewModelProviders.of(requireActivity()).get(SearchRecyclerViewModel::class.java)
        viewModelRandomAndSearch.bundleFromSearch.observe(this, {
            pokemonList = it
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.random_card, container, false)
        (view.context.applicationContext as PokemonLibraryApp).getViewModelComponent().inject(this)

        initNavigate(view)
        view.randomize_button.setOnClickListener {
            val deg: Float =  view.randomize_button.rotation + 360f
            view.randomize_button.animate().rotation(deg).setDuration(600).setInterpolator(AccelerateDecelerateInterpolator()).start()
            view.random_layout.animate().withStartAction {
                view.randomize_button.setState(false)
            }.alpha(0F).setDuration(600).withEndAction {
                randomPokemon = pokemonList!!.random()
                initCard(view, randomPokemon)
                if (!randomPokemon.isFavorite) {
                    view.add_to_fav_ig.background = ContextCompat.getDrawable(view.context, R.drawable.fav_off_dr)

                } else {
                    view.add_to_fav_ig.background = ContextCompat.getDrawable(view.context, R.drawable.fav_on_dr)

                }
                view.random_layout.animate().alpha(1F).setDuration(1000).withEndAction {
                    view.randomize_button.setState(true)
                }.start()
            }.start()
            view.add_to_fav_ig.setOnClickListener {
                if (!randomPokemon.isFavorite) {
                    view.add_to_fav_ig.background = ContextCompat.getDrawable(view.context, R.drawable.fav_on_dr)
                } else {
                    view.add_to_fav_ig.background = ContextCompat.getDrawable(view.context, R.drawable.fav_off_dr)

                }
                randomPokemon.isFavorite = !randomPokemon.isFavorite
                randomViewModel?.update(randomPokemon)
            }
        }
        return view
    }
    private fun initNavigate(view: View) {
        view.bottom_nav_view.selectedItemId = R.id.random_pokemon_fragment
        view.bottom_nav_view.setOnItemSelectedListener {
            if (it.itemId == R.id.search_pokemon_fragment) {
                viewModelRandomAndSearch.bundleFromSearch.value = pokemonList?.toMutableList()
                navigateTo(null, R.id.action_randomPokemonsFragment_to_searchedPokemonsFragment)
            }
            return@setOnItemSelectedListener true
        }
    }
}
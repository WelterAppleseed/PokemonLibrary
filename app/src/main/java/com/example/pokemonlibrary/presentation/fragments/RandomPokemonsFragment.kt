package com.example.pokemonlibrary.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.pokemonlibrary.PokemonLibraryApp
import com.example.pokemonlibrary.R
import com.example.pokemonlibrary.domain.RandomPokemonViewModel
import com.example.pokemonlibrary.domain.RandomAndSearchSharedViewModel
import com.example.pokemonlibrary.repository.database.entity.PokemonEntity
import com.example.pokemonlibrary.setState
import kotlinx.android.synthetic.main.card_item.view.*
import kotlinx.android.synthetic.main.fragment_searched_pokemons.view.*
import kotlinx.android.synthetic.main.pokemon_card.view.*
import kotlinx.android.synthetic.main.random_card.view.*
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RandomPokemonsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RandomPokemonsFragment : PokemonCardBaseFragment() {

    var randomViewModel: RandomPokemonViewModel? = null
        @Inject set
    var pokemonList: List<PokemonEntity>? = null
    lateinit var randomPokemon: PokemonEntity
    private lateinit var viewModelRandomAndSearch: RandomAndSearchSharedViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initMenuClickListener(R.id.action_favoritePokemonsFragment_to_randomPokemonsFragment, R.id.action_randomPokemonsFragment_to_favoritePokemonsFragment, null)
        viewModelRandomAndSearch = ViewModelProviders.of(requireActivity()).get(RandomAndSearchSharedViewModel::class.java)
        viewModelRandomAndSearch.bundleFromRandomToSearch.observe(this, Observer {
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
            view.random_layout.animate().withStartAction {
                view.splash.animate().alpha(1F).setDuration(600).start()
                view.randomize_button.setState(false)
            }.alpha(0F).setDuration(600).withEndAction {
                randomPokemon = pokemonList!!.random()
                initCard(view, randomPokemon)
                if (!randomPokemon.isFavorite) {
                    view.add_to_fav_ig.setImageResource(android.R.drawable.btn_star_big_off)
                } else {
                    view.add_to_fav_ig.setImageResource(android.R.drawable.btn_star_big_on)
                }
                view.splash.animate().withStartAction {
                    view.random_layout.alpha = 1F
                }.alpha(0F).setDuration(1000).withEndAction {
                    view.randomize_button.setState(true)
                }.start()
            }.start()
            view.add_to_fav_ig.setOnClickListener {
                if (!randomPokemon.isFavorite) {
                    view.add_to_fav_ig.setImageResource(android.R.drawable.btn_star_big_on)
                } else {
                    view.add_to_fav_ig.setImageResource(android.R.drawable.btn_star_big_off)
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
                viewModelRandomAndSearch.bundleFromRandomToSearch.value = pokemonList
                navigateTo(null, R.id.action_randomPokemonsFragment_to_searchedPokemonsFragment)
            }
            return@setOnItemSelectedListener true
        }
    }
}
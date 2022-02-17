package com.example.pokemonlibrary.presentation.fragments

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonlibrary.PokemonLibraryApp
import com.example.pokemonlibrary.R
import com.example.pokemonlibrary.adapter.PokemonsAdapter
import com.example.pokemonlibrary.domain.SearchPokemonViewModel
import com.example.pokemonlibrary.repository.database.entity.PokemonEntity
import com.example.pokemonlibrary.single_live_data.OnAddToFavoriteClickListener
import com.example.pokemonlibrary.single_live_data.PokemonCardClickListener
import io.reactivex.rxkotlin.toMaybe
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_favorite_pokemons.view.*
import kotlinx.android.synthetic.main.fragment_searched_pokemons.view.*
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FavoritePokemonsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoritePokemonsFragment : BaseFragment() {

    var searchPokemonViewModel: SearchPokemonViewModel? = null
        @Inject set

    private var favPokemonList = mutableListOf<PokemonEntity>()
    private var navigationId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        navigationId = arguments?.getInt("from")!!
        initMenuBackClickListener(navigationId, null)
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite_pokemons, container, false)
        (view.context.applicationContext as PokemonLibraryApp).getViewModelComponent().inject(this)
        initRecycler(view)
        return view
    }

    private fun initRecycler(view: View) {
        val manager = GridLayoutManager(context, 2)
        println(favPokemonList)
        favPokemonList = searchPokemonViewModel?.getAllFavoritesPokemon()!!
        val adapter = PokemonsAdapter(favPokemonList, pokemonClickListener, favClickListener)
        view.fav_recycler?.layoutManager = manager
        view.fav_recycler?.adapter = adapter
    }

    fun updateList(pokemon: PokemonEntity) {
        favPokemonList.add(pokemon)
        insertToFavs(pokemon.name.lowercase())
    }

    private val favClickListener = object : OnAddToFavoriteClickListener {
        override fun addToFav(pokemon: PokemonEntity) {
            updateList(pokemon)
        }

        override fun deleteFromFav(pokemon: PokemonEntity) {
            deleteFromFavoritePokemonList(pokemon)
        }
    }
    private fun deleteFromFavoritePokemonList(pokemon: PokemonEntity) {
        deleteFromFavs(pokemon.name)
        val index = favPokemonList.indexOf(pokemon)
        favPokemonList.remove(pokemon)
        view?.findViewById<RecyclerView>(R.id.fav_recycler)?.adapter?.notifyItemRemoved(index)
    }
    private val  pokemonClickListener = object : PokemonCardClickListener {
        override fun openDetail(entity: PokemonEntity) {
            openDetailItem(entity.name)
        }

    }
    private fun openDetailItem(name: String) {
        val bundle = Bundle()
        bundle.putString(context?.getString(R.string.EXTRA_NAME_ID), name)
        bundle.putInt("from_fav_frag", R.id.action_singlePokemonFragment_to_favoritePokemonsFragment)
        bundle.putInt("from", navigationId)
        navigateTo(bundle, R.id.action_favoritePokemonsFragment_to_singlePokemonFragment)
        (activity as AppCompatActivity).fav_toolbar.navigationIcon = null
    }
}
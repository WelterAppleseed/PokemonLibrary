package com.example.pokemonlibrary.presentation.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokemonlibrary.PokemonLibraryApp
import com.example.pokemonlibrary.R
import com.example.pokemonlibrary.adapter.PokemonsAdapter
import com.example.pokemonlibrary.domain.RepositoryPokemonViewModel
import com.example.pokemonlibrary.domain.SearchRecyclerViewModel
import com.example.pokemonlibrary.repository.database.entity.PokemonEntity
import com.example.pokemonlibrary.presentation.interfaces.OnAddToFavoriteClickListener
import com.example.pokemonlibrary.presentation.interfaces.PokemonCardClickListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_searched_pokemons.*
import kotlinx.android.synthetic.main.fragment_searched_pokemons.view.*
import javax.inject.Inject


class SearchedPokemonsFragment : BaseFragment() {
    private lateinit var adapter: PokemonsAdapter
    var repositoryPokemonViewModel: RepositoryPokemonViewModel? = null
        @Inject set
    private lateinit var viewModelRandomAndSearch: SearchRecyclerViewModel
    private var adapterList: List<PokemonEntity> = listOf()
    private var searchValue = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        initBackClickCallback(null, R.id.action_searchedPokemonsFragment_to_menuFragment)
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_searched_pokemons, container, false)
        getData(view)
        arguments?.getString(context?.getString(R.string.EXTRA_EDIT_TEXT_VALUE))
        view.s_bottom_nav_view.setOnItemSelectedListener {
            if (it.itemId == R.id.random_pokemon_fragment) {
                viewModelRandomAndSearch.bundleToSearch.value = adapterList.toMutableList()
                navigateTo(null, R.id.action_searchedPokemonsFragment_to_randomPokemonsFragment)
            }
            return@setOnItemSelectedListener false
        }
        initSearch(view)
        initMenuClickListener(R.id.action_favoritePokemonsFragment_to_searchedPokemonsFragment, R.id.action_searchedPokemonsFragment_to_favoritePokemonsFragment)
        return view
    }
    private fun initRecycler(view: View, list: List<PokemonEntity>) {
        adapterList = list
        val manager = GridLayoutManager(context, 2)
        adapter = PokemonsAdapter(adapterList, pokemonClickListener, favClickListener)
        view.pokemon_recycler?.layoutManager = manager
        view.pokemon_recycler?.adapter = adapter
    }
    private fun initSearch(view: View) {
        view.pokemon_name_et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (view.pokemon_recycler.adapter != null) {
                    (view.pokemon_recycler.adapter as PokemonsAdapter).filter(s.toString())
                }
            }
        })
        view.pokemon_name_et.setText(searchValue)
    }

    fun updateFavorites(pokemon: PokemonEntity, delete: Boolean) {
        if (delete) {
            deleteFromFavs(pokemon.name.lowercase())
        } else {
            insertToFavs(pokemon.name.lowercase())
        }
        repositoryPokemonViewModel?.update(pokemon)
    }
    private val favClickListener = object : OnAddToFavoriteClickListener {
        override fun addToFav(pokemon: PokemonEntity) {
            updateFavorites(pokemon, false)
        }

        override fun deleteFromFav(pokemon: PokemonEntity) {
            updateFavorites(pokemon, true)
        }
    }

    private val pokemonClickListener = object : PokemonCardClickListener {
        override fun openDetail(entity: PokemonEntity) {
            openDetailItem(entity.name)
        }
    }

    private fun openDetailItem(name: String) {
        val bundle = Bundle()
        bundle.putString(context?.getString(R.string.EXTRA_NAME_ID), name)
        if (view!!.pokemon_name_et.text.toString() == "") {
            bundle.putString(context?.getString(R.string.EXTRA_EDIT_TEXT_VALUE), view!!.pokemon_name_et.text.toString())
        }
        navigateTo(bundle, R.id.action_searchedPokemonsFragment_to_singlePokemonFragment)

    }

    private fun getData(view: View) {
        (view.context.applicationContext as PokemonLibraryApp).getViewModelComponent().inject(this)
        viewModelRandomAndSearch = ViewModelProviders.of(requireActivity()).get(SearchRecyclerViewModel::class.java)
        viewModelRandomAndSearch.bundleFromSearch.observe(this, {
            initRecycler(view, it)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onSaveInstanceState(Bundle())
    }
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(context?.getString(R.string.EXTRA_EDIT_TEXT_VALUE), view?.pokemon_name_et?.text.toString())
        Log.i("SearchedPokemonsFrag", "OnSaveInstanceState: $outState")
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        searchValue = savedInstanceState?.getString(context?.getString(R.string.EXTRA_EDIT_TEXT_VALUE)).toString()
        Log.i("SearchedPokemonsFrag", "OnViewStateRestored: $savedInstanceState")
        super.onViewStateRestored(savedInstanceState)
    }
}
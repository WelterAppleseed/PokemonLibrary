package com.example.pokemonlibrary.presentation.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokemonlibrary.PokemonLibraryApp
import com.example.pokemonlibrary.R
import com.example.pokemonlibrary.adapter.PokemonsAdapter
import com.example.pokemonlibrary.domain.SearchPokemonViewModel
import com.example.pokemonlibrary.repository.database.entity.PokemonEntity
import com.example.pokemonlibrary.single_live_data.PokemonCardClickListener
import kotlinx.android.synthetic.main.fragment_searched_pokemons.*
import kotlinx.android.synthetic.main.fragment_searched_pokemons.view.*
import javax.inject.Inject
import com.example.pokemonlibrary.domain.RandomAndSearchSharedViewModel
import com.example.pokemonlibrary.single_live_data.OnAddToFavoriteClickListener
import kotlinx.android.synthetic.main.activity_main.*


class SearchedPokemonsFragment : BaseFragment() {
    lateinit var adapter: PokemonsAdapter
    var searchPokemonViewModel: SearchPokemonViewModel? = null
        @Inject set
    private lateinit var viewModelRandomAndSearch: RandomAndSearchSharedViewModel
    var adapterList: List<PokemonEntity> = listOf()
    private var searchValue = ""
    override fun onCreate(savedInstanceState: Bundle?) {
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
                viewModelRandomAndSearch.bundleFromSearchToRandom.value = adapterList
                navigateTo(null, R.id.action_searchedPokemonsFragment_to_randomPokemonsFragment)
            }
            return@setOnItemSelectedListener false
        }
        initSearch(view)
        initMenuClickListener(R.id.action_favoritePokemonsFragment_to_searchedPokemonsFragment, R.id.action_searchedPokemonsFragment_to_favoritePokemonsFragment, null)
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
        searchPokemonViewModel?.update(pokemon)
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
        viewModelRandomAndSearch = ViewModelProviders.of(requireActivity()).get(RandomAndSearchSharedViewModel::class.java)
        viewModelRandomAndSearch.bundleFromRandomToSearch.observe(this, Observer {
            initRecycler(view, it)
        })
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
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
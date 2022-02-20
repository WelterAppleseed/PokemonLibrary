package com.example.pokemonlibrary.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonlibrary.PokemonLibraryApp
import com.example.pokemonlibrary.R
import com.example.pokemonlibrary.adapter.PokemonsAdapter
import com.example.pokemonlibrary.domain.RepositoryPokemonViewModel
import com.example.pokemonlibrary.domain.SearchRecyclerViewModel
import com.example.pokemonlibrary.presentation.interfaces.OnAddToFavoriteClickListener
import com.example.pokemonlibrary.presentation.interfaces.PokemonCardClickListener
import com.example.pokemonlibrary.replaceByName
import com.example.pokemonlibrary.repository.database.entity.PokemonEntity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_favorite_pokemons.view.*
import javax.inject.Inject


class FavoritePokemonsFragment : BaseFragment() {

    var repositoryPokemonViewModel: RepositoryPokemonViewModel? = null
        @Inject set
    private lateinit var handleListViewModel: SearchRecyclerViewModel

    private var favPokemonList = mutableListOf<PokemonEntity>()
    private var navigationId = 0
    private lateinit var pokemonList: MutableList<PokemonEntity>
    override fun onCreate(savedInstanceState: Bundle?) {
        navigationId = arguments?.getInt("from")!!
        initMenuBackClickListener(navigationId, null)
        initBackClickCallback(null, navigationId)
        handleListViewModel =
            ViewModelProviders.of(requireActivity()).get(SearchRecyclerViewModel::class.java)
        handleListViewModel.bundleFromSearch.observe(this, {
            pokemonList = it
        })
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
        favPokemonList = repositoryPokemonViewModel?.getAllFavoritesPokemon()!!
        val adapter =
            PokemonsAdapter(view.context, favPokemonList, pokemonClickListener, favClickListener)
        view.fav_recycler?.layoutManager = manager
        view.fav_recycler?.adapter = adapter
    }

    override fun onDestroyView() {
        (activity as AppCompatActivity).fav_toolbar.navigationIcon = null
        super.onDestroyView()
    }

    fun updateList(pokemon: PokemonEntity) {
        favPokemonList.add(pokemon)
    }

    private val favClickListener = object : OnAddToFavoriteClickListener {
        override fun update(pokemon: PokemonEntity) {
        }

        override fun add(pokemon: PokemonEntity) {
            updateList(pokemon)
        }

        override fun delete(pokemon: PokemonEntity) {
            deleteFromFavoritePokemonList(pokemon)
        }
    }

    private fun deleteFromFavoritePokemonList(pokemon: PokemonEntity) {
        val index = favPokemonList.indexOf(pokemon)
        favPokemonList.remove(pokemon)
        repositoryPokemonViewModel?.update(pokemon)
        pokemonList.replaceByName(pokemon)
        view?.findViewById<RecyclerView>(R.id.fav_recycler)?.adapter?.notifyItemRemoved(index)
    }

    private val pokemonClickListener = object : PokemonCardClickListener {
        override fun openDetail(entity: PokemonEntity) {
            openDetailItem(entity.name)
        }

    }

    private fun openDetailItem(name: String) {
        val bundle = Bundle()
        bundle.putString(context?.getString(R.string.EXTRA_NAME_ID), name)
        bundle.putInt(
            "from_fav_frag",
            R.id.action_singlePokemonFragment_to_favoritePokemonsFragment
        )
        bundle.putInt("from", navigationId)
        navigateTo(bundle, R.id.action_favoritePokemonsFragment_to_singlePokemonFragment)
        (activity as AppCompatActivity).fav_toolbar.navigationIcon = null
    }
}
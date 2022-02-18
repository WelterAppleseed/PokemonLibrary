package com.example.pokemonlibrary.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.example.pokemonlibrary.PokemonLibraryApp
import com.example.pokemonlibrary.R
import com.example.pokemonlibrary.domain.RepositoryPokemonViewModel
import com.example.pokemonlibrary.domain.SearchRecyclerViewModel
import kotlinx.android.synthetic.main.fragment_launch.view.*
import javax.inject.Inject

class LaunchFragment : BaseFragment() {

    private lateinit var viewModelRandomAndSearch: SearchRecyclerViewModel

    var repositoryPokemonViewModel: RepositoryPokemonViewModel? = null
        @Inject set

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_launch, container, false)
        (view.context.applicationContext as PokemonLibraryApp).getViewModelComponent().inject(this)
        initViewModel(view)
        return view

    }
    private fun initViewModel(view: View) {
        if (!isOnline(this.context!!)) {
            androidx.appcompat.app.AlertDialog.Builder(this.context!!)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Internet Connection Alert")
                .setCancelable(false)
                .setMessage("Please Check Your Internet Connection")
                .setPositiveButton(
                    "Close"
                ) { _, _ ->
                    activity?.finish()
                }.show()
        } else {
            viewModelRandomAndSearch =
                ViewModelProviders.of(requireActivity()).get(SearchRecyclerViewModel::class.java)
            repositoryPokemonViewModel?.getAllPokemon()
            repositoryPokemonViewModel?.getLiveDataPokemon()?.observe(this, {
                viewModelRandomAndSearch.bundleFromSearch.value = it.toMutableList()
                view.progress_bar.animate().alpha(0F).setDuration(1000).withEndAction {
                    navigateTo(null, R.id.action_launchFragment_to_menuFragment)
                }
            })
        }
    }
}
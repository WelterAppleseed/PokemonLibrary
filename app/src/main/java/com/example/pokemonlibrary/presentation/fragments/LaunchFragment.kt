package com.example.pokemonlibrary.presentation.fragments

import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.example.pokemonlibrary.PokemonLibraryApp
import com.example.pokemonlibrary.R
import com.example.pokemonlibrary.broadcast_reciever.NetworkStateReceiver
import com.example.pokemonlibrary.domain.RepositoryPokemonViewModel
import com.example.pokemonlibrary.domain.SearchRecyclerViewModel
import com.example.pokemonlibrary.getNoConnectionDialog
import kotlinx.android.synthetic.main.fragment_launch.view.*
import javax.inject.Inject

class LaunchFragment : BaseFragment() {

    private lateinit var viewModelRandomAndSearch: SearchRecyclerViewModel
    private lateinit var networkStateReceiver: NetworkStateReceiver
    var repositoryPokemonViewModel: RepositoryPokemonViewModel? = null
        @Inject set

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_launch, container, false)
        (view.context.applicationContext as PokemonLibraryApp).getViewModelComponent().inject(this)
        if (com.example.pokemonlibrary.isOnline(view.context)) {
            registerNetConnectionReciever()
            initViewModel(view)
        } else {
            this.activity?.getNoConnectionDialog(true)?.show()
        }
        return view

    }

    private fun initViewModel(view: View) {
        viewModelRandomAndSearch =
            ViewModelProviders.of(requireActivity()).get(SearchRecyclerViewModel::class.java)
            repositoryPokemonViewModel?.getAllPokemon(getOnlineState())
            repositoryPokemonViewModel?.getLiveDataPokemon()?.observe(this, {
            viewModelRandomAndSearch.bundleFromSearch.value = it.toMutableList()
            view.progress_bar.animate().alpha(0F).setDuration(1000).withEndAction {
                navigateTo(null, R.id.action_launchFragment_to_menuFragment)
            }
        })
    }
    private fun registerNetConnectionReciever() {
        networkStateReceiver = NetworkStateReceiver(this.activity!!.getNoConnectionDialog(false))
        val filter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        this.activity!!.registerReceiver(networkStateReceiver, filter)
    }
}
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
import com.example.pokemonlibrary.domain.SearchPokemonViewModel
import com.example.pokemonlibrary.domain.RandomAndSearchSharedViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_launch.view.*
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LaunchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LaunchFragment : BaseFragment() {

    private lateinit var viewModelRandomAndSearch: RandomAndSearchSharedViewModel

    var searchPokemonViewModel: SearchPokemonViewModel? = null
    @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
        viewModelRandomAndSearch = ViewModelProviders.of(requireActivity()).get(RandomAndSearchSharedViewModel::class.java)
        searchPokemonViewModel?.getAllPokemon()
        searchPokemonViewModel?.getLiveDataPokemon()?.observe(this, Observer {
            viewModelRandomAndSearch.bundleFromRandomToSearch.value = it
            view.progress_bar.animate().alpha(0F).setDuration(1000).withEndAction {
                navigateTo(null, R.id.action_launchFragment_to_searchedPokemonsFragment)
            }
        })
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LaunchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LaunchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
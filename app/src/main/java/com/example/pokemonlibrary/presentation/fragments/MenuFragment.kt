package com.example.pokemonlibrary.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.pokemonlibrary.PokemonLibraryApp
import com.example.pokemonlibrary.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_menu.view.*


class MenuFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBackClickCallback(null, 0)
        initMenuClickListener(R.id.action_favoritePokemonsFragment_to_menuFragment, R.id.action_menuFragment_to_favoritePokemonsFragment)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_menu, container, false)
        view.to_random_fragment_b.setOnClickListener {
            navigateTo(null, R.id.action_menuFragment_to_randomPokemonsFragment)
        }
        view.to_search_fragment_b.setOnClickListener {
            navigateTo(null, R.id.action_menuFragment_to_searchedPokemonsFragment)
        }
        return view
    }
}
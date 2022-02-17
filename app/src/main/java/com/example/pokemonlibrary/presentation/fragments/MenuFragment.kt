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


class MenuFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_menu, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
    }
}
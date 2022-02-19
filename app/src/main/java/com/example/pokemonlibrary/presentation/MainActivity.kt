package com.example.pokemonlibrary.presentation

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.example.pokemonlibrary.PokemonLibraryApp
import com.example.pokemonlibrary.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as PokemonLibraryApp).getViewModelComponent().inject(this)
        setContentView(R.layout.activity_main)
        setSupportActionBar(fav_toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.fragments_menu, menu)
        return true
    }
}
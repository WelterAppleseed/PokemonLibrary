package com.example.pokemonlibrary.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.example.pokemonlibrary.R
import com.example.pokemonlibrary.insert
import kotlinx.android.synthetic.main.activity_main.*

abstract class BaseFragment: Fragment() {
    var navOptions: NavOptions? = null
    var navController: NavController? = null
    val sharedPreferences = this.activity?.getSharedPreferences("favs", Context.MODE_PRIVATE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNavigation()
    }
    fun initNavigation() {
        navController = NavHostFragment.findNavController(this)
        navOptions = NavOptions.Builder().setEnterAnim(R.anim.nav_default_enter_anim).setExitAnim(R.anim.nav_default_exit_anim).build()
    }
    fun navigateTo(bundle: Bundle?, id: Int) {
        navController!!.navigate(id, bundle, navOptions)
    }
    fun getFavs(): MutableSet<String> {
        return sharedPreferences?.getStringSet("favs_set", mutableSetOf())!!
    }
    fun insertToFavs(item: String) {
        sharedPreferences?.insert("favs_set", item)
    }
    fun deleteFromFavs(item: String) {
        sharedPreferences?.edit()?.remove(item)?.apply()
    }
    fun initMenuBackClickListener(from: Int, backToSingleName: String?) {
        (activity as AppCompatActivity).fav_toolbar.setNavigationIcon(R.drawable.back_dr)
        (activity as AppCompatActivity).fav_toolbar.setNavigationOnClickListener {
            val bundle = Bundle()
            bundle.putString(context?.getString(R.string.EXTRA_NAME_ID), backToSingleName)
            navigateTo(bundle, from)
            (activity as AppCompatActivity).fav_toolbar.navigationIcon = null
        }
    }
    fun initMenuClickListener(from: Int, to: Int, backToSingleName: String?) {
        (activity as AppCompatActivity).fav_toolbar.setOnMenuItemClickListener {
            val bundle = Bundle()
            bundle.putInt("from", from)
            bundle.putString(context?.getString(R.string.EXTRA_NAME_ID), backToSingleName)
            navigateTo(bundle, to)
            return@setOnMenuItemClickListener true
        }
    }
}
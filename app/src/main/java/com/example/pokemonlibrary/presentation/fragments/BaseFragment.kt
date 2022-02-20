package com.example.pokemonlibrary.presentation.fragments

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.example.pokemonlibrary.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.pokemon_card.view.*


abstract class BaseFragment: Fragment() {
    private var navOptions: NavOptions? = null
    private var navController: NavController? = null
    private var sharedPreferences = this.activity?.getSharedPreferences("global", Context.MODE_PRIVATE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(fav_toolbar)
        (activity as AppCompatActivity).fav_toolbar.setTitleTextColor(ResourcesCompat.getColor(resources, R.color.white, null))
        initNavigation()
    }
    private fun initNavigation() {
        navController = NavHostFragment.findNavController(this)
        navOptions = NavOptions.Builder().setEnterAnim(R.anim.nav_default_enter_anim).setExitAnim(R.anim.nav_default_exit_anim).build()
    }
    fun navigateTo(bundle: Bundle?, id: Int) {
        navController!!.navigate(id, bundle, navOptions)
    }
    fun getGlobalPreferences(): SharedPreferences {
        return if (sharedPreferences == null) {
            context!!.getSharedPreferences("global", Context.MODE_PRIVATE)
        } else {
            sharedPreferences!!
        }
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
    fun initMenuClickListener(from: Int, to: Int) {
        (activity as AppCompatActivity).fav_toolbar.setOnMenuItemClickListener {
            val bundle = Bundle()
            bundle.putInt("from", from)
            navigateTo(bundle, to)
            return@setOnMenuItemClickListener true
        }
        if (((activity as AppCompatActivity).fav_toolbar).menu.getItem(0).icon != ResourcesCompat.getDrawable(resources, R.drawable.fav_on, null)) {
            (activity as AppCompatActivity).fav_toolbar.menu.getItem(0).setIcon(R.drawable.fav_on)
        }
    }
    fun initBackClickCallback(from: Int?, navigateFromTo: Int) {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (navigateFromTo == 0) {
                        activity?.finish()
                    } else {
                        val bundle = Bundle()
                        (activity as AppCompatActivity).fav_toolbar.navigationIcon = null
                        if (from != null) {
                            bundle.putInt("from", from)
                        }
                        navigateTo(bundle, navigateFromTo)
                    }
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }
    fun getOnlineState(): Boolean {
        return isOnline(this.context!!)
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    override fun onDestroy() {
        getGlobalPreferences().edit().clear().apply()
        super.onDestroy()
    }
}
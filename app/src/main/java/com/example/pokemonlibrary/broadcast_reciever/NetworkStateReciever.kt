package com.example.pokemonlibrary.broadcast_reciever

import android.net.ConnectivityManager

import android.net.NetworkInfo

import android.content.Intent

import android.content.BroadcastReceiver
import android.content.Context
import android.util.Log


class NetworkStateReceiver(private val alertDialog: androidx.appcompat.app.AlertDialog) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        Log.d("app", "Network connectivity change")
        if (intent.extras != null) {
            val ni = intent.extras!![ConnectivityManager.EXTRA_NETWORK_INFO] as NetworkInfo?
            if (ni != null && ni.state == NetworkInfo.State.CONNECTED) {
                Log.i("app", "Network " + ni.typeName + " connected")
            }
        }
        if (intent.extras!!.getBoolean(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)) {
            Log.d("app", "There's no network connectivity")
            alertDialog.show()
        }
    }
}
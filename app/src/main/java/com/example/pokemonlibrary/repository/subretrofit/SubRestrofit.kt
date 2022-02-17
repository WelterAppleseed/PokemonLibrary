package com.example.pokemonlibrary.repository.subretrofit

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object SubRestrofit {
    private var subRetrofit: Retrofit? = null

    fun getClient(baseUrl: String) : Retrofit {
        if (subRetrofit == null) {
            subRetrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
        return subRetrofit!!
    }
}
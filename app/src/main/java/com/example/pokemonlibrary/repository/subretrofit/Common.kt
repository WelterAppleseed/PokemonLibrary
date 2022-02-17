package com.example.pokemonlibrary.repository.subretrofit

object Common {
    private val base_url = "https://pokeapi.co/api/v2/"

    val retrofitService: SubRetrofitInterface
    get() = SubRestrofit.getClient(base_url).create(SubRetrofitInterface::class.java)
}
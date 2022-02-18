package com.example.pokemonlibrary.repository.server

import android.util.Log
import com.example.pokemonlibrary.repository.database.entity.PokemonEntity
import com.example.pokemonlibrary.repository.database.pojo.ListOfPokemonUrls
import com.example.pokemonlibrary.repository.database.pojo.PokemonResponse
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import java.util.concurrent.TimeUnit

class ServerCommunicator(private val pokemonApiService: PokemonApiService) {
    companion object {
        private const val DEFAULT_TIMEOUT = 10
        private const val DEFAULT_RETRY_ATTEMPS = 4L
    }

    fun getAllPokemons(): Single<ListOfPokemonUrls> {
        return pokemonApiService.getAllPokemons()
            .compose(singleTransformer())
            .doOnError{t: Throwable -> Log.d("ServerCommunicator", t.message.toString())}
    }

    fun getPokemonByName(name: String): Single<PokemonEntity> {
        return pokemonApiService.getPokemonByName(name).compose(singleTransformer())

    }

    private fun <T> singleTransformer(): SingleTransformer<T, T> = SingleTransformer {
        it.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .timeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .retry(DEFAULT_RETRY_ATTEMPS)
    }
}
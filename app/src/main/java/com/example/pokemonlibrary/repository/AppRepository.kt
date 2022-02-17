package com.example.pokemonlibrary.repository

import com.example.pokemonlibrary.repository.database.AppDatabase
import com.example.pokemonlibrary.repository.database.entity.*
import com.example.pokemonlibrary.repository.database.pojo.Sprites
import com.example.pokemonlibrary.repository.server.ServerCommunicator
import com.example.pokemonlibrary.repository.subretrofit.Common
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import java.util.*
import java.util.concurrent.*
import kotlin.concurrent.thread

class AppRepository(
    private val communicator: ServerCommunicator,
    private val database: AppDatabase
) {

    fun getAllPokemons(): Single<List<PokemonEntity>>? {
        return communicator.getAllPokemons()
            .flatMap {
                val service = Common.retrofitService
                var pokList = mutableListOf<PokemonEntity>()
                if (database.allPokemonDao().getAll().size != it.count) {
                    val syncList: CopyOnWriteArrayList<PokemonEntity> =
                        CopyOnWriteArrayList<PokemonEntity>()
                    for (i in 0 until it.count) {
                        thread {
                            syncList.add(service.getPokemon(it.results[i].name).blockingGet())
                        }
                    }
                    Thread.sleep(2000)
                    pokList = syncList.toMutableList()
                }
                database.allPokemonDao().insert(pokList)
                Single.just(database.allPokemonDao().getAll())
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


    fun getPokemon(name: String): Single<PokemonEntity> {
        return communicator.getPokemonByName(name)
            .map {
                return@map it
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
    fun update(pokemonEntity: PokemonEntity) {
        println("121")
        database.allPokemonDao().insert(pokemonEntity)
    }

    fun getFavorites(): MutableList<PokemonEntity> {
        return (database.allPokemonDao().getFavoritePokemons(true) as MutableList<PokemonEntity>)
    }

}
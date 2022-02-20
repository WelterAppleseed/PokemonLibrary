package com.example.pokemonlibrary.di.module

import com.example.pokemonlibrary.BuildConfig
import com.example.pokemonlibrary.repository.server.PokemonApiService
import com.example.pokemonlibrary.repository.server.ServerCommunicator
import com.example.pokemonlibrary.di.scope.ApiScope
import dagger.Module
import dagger.Provides
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class ApiModule {
    companion object {
        const val API_URL = "https://pokeapi.co/api/v2/"
    }
    @Provides
    @ApiScope
    fun provideCommunicator(pokemonApiService: PokemonApiService): ServerCommunicator {
        return ServerCommunicator(pokemonApiService)
    }

    @Provides
    @ApiScope
    fun provideApiService(retrofit: Retrofit): PokemonApiService {
        return retrofit.create<PokemonApiService>(PokemonApiService::class.java)
    }

    @Provides
    @ApiScope
    fun provideRetrofit(builder: Retrofit.Builder): Retrofit {
        return builder.baseUrl(API_URL).build()
    }

    @Provides
    @ApiScope
    fun provideRetrofitBuilder(): Retrofit.Builder {
        val builder = OkHttpClient.Builder()
            .connectionPool(ConnectionPool(5, 30, TimeUnit.SECONDS))
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val httpLoginInterceptor = HttpLoggingInterceptor()
            httpLoginInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(httpLoginInterceptor)
        }
        return Retrofit.Builder()
            .client(builder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    }
}
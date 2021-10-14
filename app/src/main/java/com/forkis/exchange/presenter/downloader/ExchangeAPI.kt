package com.forkis.exchange.presenter.downloader

import com.forkis.exchange.model.Currencies
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//https://www.nbrb.by/api/exrates/rates?ondate=2016-7-6&periodicity=0

interface ExchangeAPI {

    companion object{
        const val BASE_URL = "https://www.nbrb.by/api/exrates/"
        operator fun invoke():ExchangeAPI{
            return Retrofit.Builder().baseUrl(BASE_URL).
            addConverterFactory(GsonConverterFactory.create()).
            build().
            create(ExchangeAPI::class.java)
        }
    }


    @GET("./rates")
    suspend fun getRates(
        @Query("ondate") date: String,
        @Query("periodicity") period: String = "0"
    ): Response<Currencies>


}
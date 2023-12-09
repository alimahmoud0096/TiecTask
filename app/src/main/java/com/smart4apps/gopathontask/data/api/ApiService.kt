package com.smart4apps.gopathontask.data.api

import com.smart4apps.gopathontask.data.model.currencies.CurrenciesModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {


    @GET("latest")
    suspend fun listRatesWithBase(
        @Query("access_key") api_key: String = ApiURL.API_KEY
    ):CurrenciesModel

    @GET("{date}")
    suspend fun getHistoricalData(
        @Path("date") date:String,
        @Query("symbols") symbols:String?,
        @Query("access_key") api_key: String = ApiURL.API_KEY
    ):CurrenciesModel
}
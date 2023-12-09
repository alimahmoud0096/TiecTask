package com.smart4apps.gopathontask.data.repository

import com.smart4apps.gopathontask.data.api.ApiService
import com.smart4apps.gopathontask.data.model.currencies.CurrenciesModel
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiService: ApiService) {


    suspend fun listRatesWithBase(): CurrenciesModel {
        return apiService.listRatesWithBase()
    }

    suspend fun getHistoricalData(date:String,
                                symbols:String?,): CurrenciesModel {
        return apiService.getHistoricalData(date, symbols)
    }
}
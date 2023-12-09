package com.smart4apps.gopathontask.data.repository

import com.smart4apps.gopathontask.data.model.currencies.ConvertHistory
import com.smart4apps.gopathontask.data.room.CurrencyDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CurrencyRepository @Inject constructor(
    private val currencyDao: CurrencyDao
) {


     fun getAllConvertedHistory(): Flow<List<ConvertHistory>> {
        return currencyDao.getAllConvertedHistory()
    }

    suspend fun insertConvertHistory(convertHistory: ConvertHistory) {
        currencyDao.insertConvertHistory(convertHistory)
    }

}
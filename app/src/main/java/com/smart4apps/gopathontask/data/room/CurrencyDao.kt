package com.smart4apps.gopathontask.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.smart4apps.gopathontask.data.model.currencies.ConvertHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {


    @Query("SELECT * FROM converthistory")
    fun getAllConvertedHistory(): Flow<List<ConvertHistory>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConvertHistory(convertHistory: ConvertHistory)
}
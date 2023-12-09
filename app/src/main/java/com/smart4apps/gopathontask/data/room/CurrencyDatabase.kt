package com.smart4apps.gopathontask.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.smart4apps.gopathontask.data.model.currencies.ConvertHistory

@Database(entities = [ConvertHistory::class], version = 1)
abstract class CurrencyDatabase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao

}
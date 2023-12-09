package com.smart4apps.gopathontask.di

import android.content.Context
import androidx.room.Room
import com.smart4apps.gopathontask.data.api.ApiService
import com.smart4apps.gopathontask.data.repository.CurrencyRepository
import com.smart4apps.gopathontask.data.repository.MainRepository
import com.smart4apps.gopathontask.data.room.CurrencyDao
import com.smart4apps.gopathontask.data.room.CurrencyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataRepositoryModule {

    @Provides
    fun provideDataRepository(apiService: ApiService): MainRepository {
        return MainRepository(apiService)
    }

    @Provides
    fun provideCurrencyRepository(currencyDao: CurrencyDao): CurrencyRepository {
        return CurrencyRepository(currencyDao)
    }

    @Provides
    fun provideCurrencyDao(appDatabase: CurrencyDatabase): CurrencyDao {
        return appDatabase.currencyDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): CurrencyDatabase {
        return Room.databaseBuilder(
            appContext,
            CurrencyDatabase::class.java,
            "CurrencyDatabase"
        ).build()
    }
}
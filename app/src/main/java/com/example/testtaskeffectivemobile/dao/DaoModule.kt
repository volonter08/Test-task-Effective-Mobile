package com.example.testtaskeffectivemobile.dao

import com.example.testtaskeffectivemobile.db.AppDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Provides
    @Singleton
    fun provideProfileDao(appDb: AppDb): ProfileDao =
        appDb.profileDao()
    @Provides
    @Singleton
    fun provideProductDao(appDb: AppDb): ProductDao =
        appDb.productDao()
}
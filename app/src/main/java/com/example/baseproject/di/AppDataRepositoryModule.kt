package com.example.baseproject.di

import com.example.baseproject.data.DataRepository
import com.example.baseproject.data.DataRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ViewModelComponent::class)
class AppDataRepositoryModule {
    @Provides
    fun provideDataRepository(): DataRepository = DataRepositoryImpl()
}
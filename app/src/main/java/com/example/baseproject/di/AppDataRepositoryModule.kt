package com.example.baseproject.di

import androidx.appcompat.app.AppCompatActivity
import com.example.baseproject.data.DataRepository
import com.example.baseproject.data.DataRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class AppDataRepositoryModule {
    @Provides
    fun provideDataRepository() : DataRepository = DataRepositoryImpl()
}
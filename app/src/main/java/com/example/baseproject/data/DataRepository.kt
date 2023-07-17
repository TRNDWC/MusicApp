package com.example.baseproject.data

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import java.io.File

interface DataRepository {
    fun getData(file: File): List<File>
}
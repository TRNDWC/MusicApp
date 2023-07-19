package com.example.baseproject.data

import android.content.Context
import java.io.File

interface DataRepository {
    fun getData(file: File): List<File>

    fun getSong(context: Context) : List<PlaylistSongItem>
}
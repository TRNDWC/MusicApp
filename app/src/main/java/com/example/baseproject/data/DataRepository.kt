package com.example.baseproject.data

import android.content.Context

interface DataRepository {
    fun getSong(context: Context): List<PlaylistSongItem>
}
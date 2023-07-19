package com.example.baseproject.data

import androidx.lifecycle.LiveData

class SongRepository(private val songDao: SongDao) {
    val getAllSong: LiveData<List<PlaylistSongItem>> = songDao.listAllSong()
    suspend fun addSong(item: PlaylistSongItem) {
        songDao.addSong(item)
    }

    suspend fun clearData() {
        songDao.clearData()
    }
}
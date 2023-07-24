package com.example.baseproject.data.songrepo

import androidx.lifecycle.LiveData
import com.example.baseproject.data.model.PlaylistSongItem

class SongRepository(private val songDao: SongDao) {

    val getAllSong: LiveData<List<PlaylistSongItem>> = songDao.listAllSong()
    suspend fun addSong(item: PlaylistSongItem) {
        songDao.addSong(item)
    }

    suspend fun clearData() {
        songDao.clearData()
    }
}
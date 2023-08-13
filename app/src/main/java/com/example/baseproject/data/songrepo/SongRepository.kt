package com.example.baseproject.data.songrepo

import androidx.lifecycle.LiveData
import com.example.baseproject.data.model.PlaylistSongItem
import com.example.baseproject.data.songrepo.SongDao

class SongRepository(private val songDao: SongDao) {

    suspend fun getAllSong() : List<PlaylistSongItem>{
        return songDao.listAllSong()
    }
    suspend fun addSong(item: PlaylistSongItem) {
        songDao.addSong(item)
    }

    suspend fun clearData() {
        songDao.clearData()
    }
}
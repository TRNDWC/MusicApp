package com.example.baseproject.data.playlistrepo

import androidx.lifecycle.LiveData
import com.example.baseproject.data.model.LibraryItem
import com.example.baseproject.data.playlistrepo.PlaylistDao

class PlaylistRepository(private val playlistDao: PlaylistDao) {
    suspend fun getAllPlaylist() : List<LibraryItem>{
        return playlistDao.listAllPlaylist()
    }
    suspend fun addPlaylist(item: LibraryItem) {
        playlistDao.addPlaylist(item)
    }
}
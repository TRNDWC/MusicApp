package com.example.baseproject.data

import androidx.lifecycle.LiveData

class PlaylistRepository(private val playlistDao: PlaylistDao) {
    
    val getAllPlaylist: LiveData<List<LibraryItem>> = playlistDao.listAllPlaylist()
    suspend fun addPlaylist(item: LibraryItem) {
        playlistDao.addPlaylist(item)
    }
}
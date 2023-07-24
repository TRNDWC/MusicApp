package com.example.baseproject.data.playlistrepo

import androidx.lifecycle.LiveData
import com.example.baseproject.data.model.LibraryItem

class PlaylistRepository(private val playlistDao: PlaylistDao) {

    val getAllPlaylist: LiveData<List<LibraryItem>> = playlistDao.listAllPlaylist()
    suspend fun addPlaylist(item: LibraryItem) {
        playlistDao.addPlaylist(item)
    }
}
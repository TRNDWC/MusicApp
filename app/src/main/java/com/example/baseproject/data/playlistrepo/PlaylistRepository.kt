package com.example.baseproject.data.playlistrepo

import com.example.baseproject.data.model.LibraryItem

class PlaylistRpository(private val playlistDao: PlaylistDao) {
    suspend fun getAllPlaylist() : List<LibraryItem>{
        return playlistDao.listAllPlaylist()
    }
    suspend fun addPlaylist(item: LibraryItem) {
        playlistDao.addPlaylist(item)
    }
}
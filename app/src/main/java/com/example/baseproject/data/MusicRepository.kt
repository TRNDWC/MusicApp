package com.example.baseproject.data

import com.example.baseproject.data.relation.SongPlaylistCrossRef

class MusicRepository(private val musicDao: MusicDao) {

    fun getSongsOfPlaylist(id: Int) {
        musicDao.getSongsofPlaylist(id)
    }

    fun getPlaylistsOfSong(id: Int) {
        musicDao.getPlaylistsOfSong(id)
    }

    suspend fun addSongPlaylistCrossRef(crossRef: SongPlaylistCrossRef) {
        musicDao.addSongPlaylistCrossRef(crossRef)
    }
}
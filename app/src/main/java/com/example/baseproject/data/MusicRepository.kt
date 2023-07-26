package com.example.baseproject.data

import com.example.baseproject.data.model.LibraryItem
import com.example.baseproject.data.model.PlaylistSongItem
import com.example.baseproject.data.relation.PlaylistWithSongs
import com.example.baseproject.data.relation.SongPlaylistCrossRef
import com.example.baseproject.data.relation.SongWithPlaylists

class MusicRepository(private val musicDao: MusicDao) {
    suspend fun addSong(item: PlaylistSongItem) {
        musicDao.addSong(item)
    }

    suspend fun getAllSong(): List<PlaylistSongItem> {
        return musicDao.listAllSong()
    }

    suspend fun getAllPlaylist(): List<LibraryItem> {
        return musicDao.listAllPlaylist()
    }

    suspend fun addPlaylist(item: LibraryItem) {
        musicDao.addPlaylist(item)
    }

    suspend fun getSongsOfPlaylist(id: Int): PlaylistWithSongs {
        return musicDao.getSongsofPlaylist(id)
    }

    suspend fun getPlaylistsOfSong(id: Int): SongWithPlaylists {
        return musicDao.getPlaylistsOfSong(id)
    }

    suspend fun addSongPlaylistCrossRef(crossRef: SongPlaylistCrossRef) {
        musicDao.addSongPlaylistCrossRef(crossRef)
    }
}
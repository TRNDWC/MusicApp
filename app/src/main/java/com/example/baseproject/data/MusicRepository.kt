package com.example.baseproject.data

import com.example.baseproject.data.model.LibraryItem
import com.example.baseproject.data.model.PlaylistSongItem
import com.example.baseproject.data.relation.PlaylistWithSongs
import com.example.baseproject.data.relation.SongPlaylistCrossRef
import com.example.baseproject.data.relation.SongWithPlaylists
import com.example.setting.ui.home.HomeResponse
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.flow

class MusicRepository(private val musicDao: MusicDao) {
    suspend fun addSong(item: PlaylistSongItem) {
        musicDao.addSong(item)
    }

    fun getAllSong() = flow {
        coroutineScope {
            val listSong = async { musicDao.listAllSong() }
            emit(listSong.await())
        }
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

    suspend fun getPLaylistSize(id: Int): Int {
        return musicDao.getSongsofPlaylist(id).songs.size
    }

    suspend fun addSongPlaylistCrossRef(crossRef: SongPlaylistCrossRef) {
        musicDao.addSongPlaylistCrossRef(crossRef)
    }
}
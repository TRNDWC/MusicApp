package com.example.baseproject.ui.home

import android.app.Application
import android.database.CrossProcessCursor
import androidx.lifecycle.viewModelScope
import com.example.baseproject.data.MusicDatabase
import com.example.baseproject.data.MusicRepository
import com.example.baseproject.data.model.LibraryItem
import com.example.baseproject.data.relation.SongPlaylistCrossRef
import com.example.baseproject.data.repository.playlist.PlaylistRepositoryFB
import com.example.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    playlistRepositoryFB: PlaylistRepositoryFB
) : BaseViewModel() {
    private val repository: MusicRepository

    init {
        val musicDao = MusicDatabase.getDatabase(application).musicDao()
        repository = MusicRepository(musicDao)
    }

    val data = playlistRepositoryFB.getPlaylist()

    fun setup(list: List<LibraryItem>) {
        list.forEach {
            viewModelScope.launch(Dispatchers.IO) {
                repository.addPlaylist(it)
            }
        }
    }

    val crossRefData = playlistRepositoryFB.getCrossRef()

    fun setupCrossRef(list: List<SongPlaylistCrossRef>) {
        list.forEach {
            viewModelScope.launch {
                repository.addSongPlaylistCrossRef(it)
            }
        }
    }


//    val playlists = SingleLiveEvent<List<LibraryItem>>()
//    fun getAllPlaylists() {
//        viewModelScope.launch(Dispatchers.IO) {
//            val data = repository.getAllPlaylist()
//            playlists.postValue(data)
//        }
//    }
}

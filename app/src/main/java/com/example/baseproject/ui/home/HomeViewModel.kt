package com.example.baseproject.ui.home

import android.app.Application
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
import java.io.File
import java.net.URI
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

    var playlistsList = playlistRepositoryFB.getPlaylist()
    val crossRefData = playlistRepositoryFB.getCrossRef()

    fun setup(list: List<LibraryItem>) {
        viewModelScope.launch(Dispatchers.IO) {
            list.forEach {
                repository.addPlaylist(it)
            }
        }
    }

    fun setupCrossRef(list: List<SongPlaylistCrossRef>) {
        list.forEach {
            viewModelScope.launch {
                repository.addSongPlaylistCrossRef(it)
            }
        }
    }
}

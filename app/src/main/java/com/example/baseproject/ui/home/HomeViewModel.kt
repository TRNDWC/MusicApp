package com.example.baseproject.ui.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.baseproject.data.MusicDatabase
import com.example.baseproject.data.MusicRepository
import com.example.baseproject.data.model.LibraryItem
import com.example.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application
) : BaseViewModel() {
    private val repository: MusicRepository

    init {
        val musicDao = MusicDatabase.getDatabase(application).musicDao()
        repository = MusicRepository(musicDao)
    }

    private val _playlistList = MutableLiveData<List<LibraryItem>>()
    val playlistList: LiveData<List<LibraryItem>> = _playlistList
    fun listAll() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.getAllPlaylist()
            _playlistList.postValue(data)
        }
    }

    private val _tPlaylistList = MutableLiveData<List<LibraryItem>>()
    val tPlaylistList: LiveData<List<LibraryItem>> = _tPlaylistList
    fun getPlaylistOfSong(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.getPlaylistsOfSong(id).playlists
            _tPlaylistList.postValue(data)
        }
    }
}

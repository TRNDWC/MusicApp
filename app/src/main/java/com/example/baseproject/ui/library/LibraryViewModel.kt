package com.example.baseproject.ui.library

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.baseproject.data.MusicDatabase
import com.example.baseproject.data.MusicRepository
import com.example.baseproject.data.model.LibraryItem
import com.example.baseproject.data.repository.playlist.PlaylistRepositoryFB
import com.example.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    application: Application
) : BaseViewModel() {
    private val _playlistList = MutableLiveData<List<LibraryItem>>()
    val playlistList: LiveData<List<LibraryItem>> = _playlistList
    private val repository: MusicRepository

    init {
        val musicDao = MusicDatabase.getDatabase(application).musicDao()
        repository = MusicRepository(musicDao)
    }

    fun getPlaylists() {
        viewModelScope.launch(Dispatchers.IO) {
            _playlistList.postValue(repository.getAllPlaylist())
        }
    }

    fun addPlaylist(item: LibraryItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addPlaylist(item)
            _playlistList.postValue(repository.getAllPlaylist())
        }
    }
    
    private val _newPlaylist = MutableLiveData("")
    val newPlaylist: LiveData<String> = _newPlaylist

    fun creatPl(title: String) {
        _newPlaylist.value = title
    }

    fun set() {
        _newPlaylist.value = ""
    }
}
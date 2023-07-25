package com.example.baseproject.ui.library

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.baseproject.data.model.LibraryItem
import com.example.baseproject.data.playlistrepo.PlaylistDatabase
import com.example.baseproject.data.playlistrepo.PlaylistRepository
import com.example.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    application: Application
) : BaseViewModel() {
    var playlistList: LiveData<List<LibraryItem>>
    private val repository: PlaylistRepository

    init {
        val playlistDao = PlaylistDatabase.getDatabase(application).playlistDao()
        repository = PlaylistRepository(playlistDao)
        playlistList = repository.getAllPlaylist
    }

    fun addPlaylist(item: LibraryItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addPlaylist(item)
            playlistList = repository.getAllPlaylist
        }
    }

    private val _newPlaylist = MutableLiveData<String>("")
    val newPlaylist: LiveData<String> = _newPlaylist

    fun creatPl(title: String) {
        _newPlaylist.value = title
    }

    fun set() {
        _newPlaylist.value = ""
    }
}
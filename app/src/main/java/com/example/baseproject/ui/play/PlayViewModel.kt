package com.example.baseproject.ui.play

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
class PlayViewModel @Inject constructor(
    application: Application
) : BaseViewModel() {
    private val repository: MusicRepository

    init {
        val musicDao = MusicDatabase.getDatabase(application).musicDao()
        repository = MusicRepository(musicDao)
    }

//    val songDescription: MutableLiveData<String> by lazy {
//        MutableLiveData<String>()
//    }
//    private val _tPlaylistList = MutableLiveData<List<Int>>()
//
//    val tPlaylistList: LiveData<List<Int>> = _tPlaylistList
//    fun getPlaylistOfSong(id: Int) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val data = repository.getPlaylistsOfSong(id).playlists
//            val list = mutableListOf<Int>()
//            data.forEach { it ->
//                list.add(it.playlistId)
//            }
//            _tPlaylistList.postValue(list)
//        }
//    }
}
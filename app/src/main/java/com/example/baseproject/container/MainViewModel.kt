package com.example.baseproject.container

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.baseproject.data.DataRepository
import com.example.baseproject.data.PlaylistSongItem
import com.example.baseproject.data.SongDatabase
import com.example.baseproject.data.SongRepository
import com.example.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(data: DataRepository, application: Application) :
    BaseViewModel() {

    val mData = data
    val mApplication = application
    val songDao = SongDatabase.getDatabase(mApplication).songDao()
    val repository = SongRepository(songDao)
    fun getSong() {
        val songData = mData.getSong(mApplication)
        for (items in songData) {
            addSong(items)
        }
    }

    fun addSong(item: PlaylistSongItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addSong(item)
        }
    }

    fun clear() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearData()
        }
    }
}
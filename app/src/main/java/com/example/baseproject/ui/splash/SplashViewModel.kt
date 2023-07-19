package com.example.baseproject.ui.splash

import android.app.Activity
import android.app.Application
import android.os.Environment
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.baseproject.R
import com.example.baseproject.data.DataRepository
import com.example.baseproject.data.PlaylistSongItem
import com.example.baseproject.data.SongDatabase
import com.example.baseproject.data.SongRepository
import com.example.core.base.BaseViewModel
import com.example.core.utils.SingleLiveEvent
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(data: DataRepository, application: Application) :
    BaseViewModel() {

    val actionSPlash = SingleLiveEvent<SplashActionState>()
    val splashTitle = MutableLiveData(R.string.splash)
    private val repository: SongRepository

    init {
        val songDao = SongDatabase.getDatabase(application).songDao()
        repository = SongRepository(songDao)

        clear()
        val songData = data.getSong(application)
        for (items in songData) {
            val item = PlaylistSongItem(
                0,
                R.drawable.green_play_circle,
                items.songTitle,
                items.artists
            )
            addSong(item)
        }

        viewModelScope.launch {
            delay(5000)
            actionSPlash.value = SplashActionState.Finish
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

sealed class SplashActionState {
    object Finish : SplashActionState()
}
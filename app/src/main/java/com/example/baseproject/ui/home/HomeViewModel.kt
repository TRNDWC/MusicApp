package com.example.baseproject.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.baseproject.data.model.PlaylistSongItem
import com.example.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _songItem = MutableLiveData<PlaylistSongItem>()
    val songItem : LiveData<PlaylistSongItem> = _songItem

    fun getClickedItem(item: PlaylistSongItem){
        _songItem.value = item
    }

}

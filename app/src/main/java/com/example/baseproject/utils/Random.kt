package com.example.baseproject.utils

import android.util.Log
import com.example.baseproject.data.model.PlaylistSongItem
import java.util.Collections

class Random {

    fun getRandomSongList(songList: List<PlaylistSongItem>) : List<PlaylistSongItem> {
        val shuffleList = mutableListOf<PlaylistSongItem>()
        for(i in songList.indices) {
           shuffleList.add(songList[i])
        }
        shuffleList.shuffle()
        return shuffleList
    }
}
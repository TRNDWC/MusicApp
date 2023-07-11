package com.example.baseproject.ui.library

import androidx.lifecycle.ViewModel
import com.example.baseproject.R
import com.example.baseproject.ui.playlist.PlaylistSongItem
import com.example.core.base.BaseViewModel

class LibraryViewModel : BaseViewModel() {
    fun songItemList(): List<PlaylistSongItem> {
        val songItemList = mutableListOf<PlaylistSongItem>()
        songItemList.add(
            PlaylistSongItem(
                R.drawable.green_play_circle,
                "Có ai hẹn hò cùng em chưa",
                "Quân AP"
            )
        )
        songItemList.add(
            PlaylistSongItem(
                R.drawable.green_play_circle,
                "Đưa em về nhà",
                "GreyD, Chillies"
            )
        )
        songItemList.add(
            PlaylistSongItem(
                R.drawable.green_play_circle,
                "Nếu lúc đó",
                "TLinh"
            )
        )
        songItemList.add(
            PlaylistSongItem(
                R.drawable.green_play_circle,
                "Có ai hẹn hò cùng em chưa",
                "Quân AP"
            )
        )
        songItemList.add(
            PlaylistSongItem(
                R.drawable.green_play_circle,
                "Đưa em về nhà",
                "GreyD, Chillies"
            )
        )
        songItemList.add(
            PlaylistSongItem(
                R.drawable.green_play_circle,
                "Nếu lúc đó",
                "TLinh"
            )
        )
        songItemList.add(
            PlaylistSongItem(
                R.drawable.green_play_circle,
                "Có ai hẹn hò cùng em chưa",
                "Quân AP"
            )
        )
        songItemList.add(
            PlaylistSongItem(
                R.drawable.green_play_circle,
                "Đưa em về nhà",
                "GreyD, Chillies"
            )
        )
        songItemList.add(
            PlaylistSongItem(
                R.drawable.green_play_circle,
                "Nếu lúc đó",
                "TLinh"
            )
        )
        songItemList.add(
            PlaylistSongItem(
                R.drawable.green_play_circle,
                "Có ai hẹn hò cùng em chưa",
                "Quân AP"
            )
        )
        songItemList.add(
            PlaylistSongItem(
                R.drawable.green_play_circle,
                "Đưa em về nhà",
                "GreyD, Chillies"
            )
        )
        songItemList.add(
            PlaylistSongItem(
                R.drawable.green_play_circle,
                "Nếu lúc đó",
                "TLinh"
            )
        )
        return songItemList
    }
}
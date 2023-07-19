package com.example.baseproject.data

object AllSong {
    val songs :MutableList<PlaylistSongItem> = mutableListOf<PlaylistSongItem>()
    fun add(item: PlaylistSongItem){
        songs.add(item)
    }

}
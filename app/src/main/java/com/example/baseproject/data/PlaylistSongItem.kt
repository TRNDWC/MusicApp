package com.example.baseproject.data

import android.media.Image
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "song_data")
data class PlaylistSongItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var songImage: Int,
    var songTitle: String,
    var artists: String,
)
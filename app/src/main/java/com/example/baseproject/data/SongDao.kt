package com.example.baseproject.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface SongDao {
    @Upsert
    suspend fun addSong(song: PlaylistSongItem)

    @Query("SELECT * FROM song_data ORDER BY songTitle ASC")
    fun listAllSong(): LiveData<List<PlaylistSongItem>>

    @Query("DELETE FROM song_data")
    suspend fun clearData()
}
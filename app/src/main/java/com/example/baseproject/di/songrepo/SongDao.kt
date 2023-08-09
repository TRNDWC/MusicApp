package com.example.baseproject.di.songrepo

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.baseproject.data.model.PlaylistSongItem

@Dao
interface SongDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSong(song: PlaylistSongItem)

    @Query("SELECT * FROM song_data ORDER BY songTitle ASC")
    suspend fun listAllSong(): List<PlaylistSongItem>

    @Query("DELETE FROM song_data")
    suspend fun clearData()
}
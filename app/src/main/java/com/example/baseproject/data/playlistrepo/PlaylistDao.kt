package com.example.baseproject.data.playlistrepo

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.baseproject.data.model.LibraryItem

@Dao
interface PlaylistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPlaylist(playlist: LibraryItem)

    @Query("SELECT * FROM playlist_data ORDER BY playlistTitle ASC")
    suspend fun listAllPlaylist(): List<LibraryItem>
}
package com.example.baseproject.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface PlaylistDao {
    @Upsert
    suspend fun addPlaylist(playlist: LibraryItem)

    @Query("SELECT * FROM playlist_data ORDER BY playlistTitle ASC")
    fun listAllPlaylist(): LiveData<List<LibraryItem>>
}
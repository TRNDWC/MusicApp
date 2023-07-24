package com.example.baseproject.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.baseproject.data.relation.PlaylistWithSongs
import com.example.baseproject.data.relation.SongPlaylistCrossRef
import com.example.baseproject.data.relation.SongWithPlaylists

@Dao
interface MusicDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSongPlaylistCrossRef(crossRef: SongPlaylistCrossRef)

    @Transaction
    @Query("SELECT * FROM playlist_data WHERE playlistId = :playlistId")
    fun getSongsofPlaylist(playlistId: Int): LiveData<List<PlaylistWithSongs>>

    @Transaction
    @Query("SELECT * FROM song_data WHERE songId = :songId")
    fun getPlaylistsOfSong(songId: Int): LiveData<List<SongWithPlaylists>>
}
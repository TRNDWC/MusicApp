package com.example.baseproject.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.baseproject.data.model.LibraryItem
import com.example.baseproject.data.model.PlaylistSongItem
import com.example.baseproject.data.relation.PlaylistWithSongs
import com.example.baseproject.data.relation.SongPlaylistCrossRef
import com.example.baseproject.data.relation.SongWithPlaylists

@Dao
interface MusicDao {
    //Song
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSong(song: PlaylistSongItem)
    @Query("SELECT * FROM song_data ORDER BY songTitle ASC")
    suspend fun listAllSong(): List<PlaylistSongItem>

    //Playlist
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPlaylist(playlist: LibraryItem)
    @Query("SELECT * FROM playlist_data ORDER BY playlistTitle ASC")
    suspend fun listAllPlaylist(): List<LibraryItem>
    //Music
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSongPlaylistCrossRef(crossRef: SongPlaylistCrossRef)
    @Transaction
    @Query("SELECT * FROM playlist_data WHERE playlistId = :playlistId")
    suspend fun getSongsofPlaylist(playlistId: Int): PlaylistWithSongs
    @Transaction
    @Query("SELECT * FROM song_data WHERE songId = :songId")
    suspend fun getPlaylistsOfSong(songId: Int): SongWithPlaylists
}
package com.example.baseproject.data.repository.playlist

import androidx.lifecycle.MutableLiveData
import com.example.baseproject.data.model.LibraryItem
import com.example.baseproject.data.relation.SongPlaylistCrossRef
import com.example.baseproject.utils.Response

interface PlaylistRepositoryFB {
    fun getPlaylist(): MutableLiveData<Response<List<LibraryItem>>>
    suspend fun updatePlaylists(list: List<LibraryItem>): Response<Boolean>
    fun getCrossRef(): MutableLiveData<Response<List<SongPlaylistCrossRef>>>
    suspend fun updateCrossRef(list: List<SongPlaylistCrossRef>): Response<Boolean>
}
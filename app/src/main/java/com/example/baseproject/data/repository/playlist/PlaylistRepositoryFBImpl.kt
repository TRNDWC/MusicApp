package com.example.baseproject.data.repository.playlist

import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import com.example.baseproject.data.model.LibraryItem
import com.example.baseproject.data.relation.SongPlaylistCrossRef
import com.example.baseproject.utils.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await


class PlaylistRepositoryFBImpl : PlaylistRepositoryFB {
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()
    private val storage = FirebaseStorage.getInstance()

    override fun getPlaylist(): MutableLiveData<Response<List<LibraryItem>>> {
        Log.d("trndwcs", "getting")
        val playlistResponse = MutableLiveData<Response<List<LibraryItem>>>()
        var lists = mutableListOf<LibraryItem>()
        database.reference.child("users").child(auth.uid!!).child("playlists")
            .addValueEventListener(object : com.google.firebase.database.ValueEventListener {
                override fun onDataChange(snapshot: com.google.firebase.database.DataSnapshot) {
                    lists.clear()
                    for (postSnapshot in snapshot.children) {
                        val playlist: LibraryItem? = postSnapshot.getValue(LibraryItem::class.java)
                        if (playlist != null) {
                            lists.add(playlist)
                        }
                        Log.d("trndwcs","${playlist?.playlistId} ${playlist?.playlistTitle} ${playlist?.playlistImage}")
                    }
                    playlistResponse.postValue(Response.Success(lists))
                }

                override fun onCancelled(error: com.google.firebase.database.DatabaseError) {
                    playlistResponse.postValue(Response.Failure(error.toException()))
                }
            })
        return playlistResponse
    }


    override suspend fun updatePlaylists(list: List<LibraryItem>): Response<Boolean> {
        return try {
            val data = mutableListOf<LibraryItem>()
            list.forEach { item ->
                var url: String? = null
                if (item.playlistImage != null) {
                    val storageRef = storage.reference
                    val fileRef =
                        storageRef.child("playlist_picture/${auth.uid}/${item.playlistId}/${item.playlistTitle}")
                    fileRef.putFile(item.playlistImage!!.toUri()).await()
                    url = fileRef.downloadUrl.await().toString()
                }
                val item = LibraryItem(item.playlistId, item.playlistTitle, url)
                data.add(item)
            }
            database.reference.child("users").child(auth.uid!!).apply {
                child("playlists").setValue(data)
            }
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override fun getCrossRef(): MutableLiveData<Response<List<SongPlaylistCrossRef>>> {
        val playlistResponse = MutableLiveData<Response<List<SongPlaylistCrossRef>>>()
        val lists = mutableListOf<SongPlaylistCrossRef>()
        database.reference.child("users").child(auth.uid!!).child("cross_refs")
            .addValueEventListener(object : com.google.firebase.database.ValueEventListener {
                override fun onDataChange(snapshot: com.google.firebase.database.DataSnapshot) {
                    lists.clear()
                    for (postSnapshot in snapshot.children) {
                        val item: SongPlaylistCrossRef? =
                            postSnapshot.getValue(SongPlaylistCrossRef::class.java)
                        if (item != null) {
                            lists.add(item)
                        }
                    }
                    playlistResponse.postValue(Response.Success(lists))
                }

                override fun onCancelled(error: com.google.firebase.database.DatabaseError) {
                    playlistResponse.postValue(Response.Failure(error.toException()))
                }
            })
        return playlistResponse
    }

    override suspend fun updateCrossRef(list: List<SongPlaylistCrossRef>): Response<Boolean> {
        return try {
            database.reference.child("users").child(auth.uid!!).apply {
                child("cross_refs").setValue(list)
            }
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }
}
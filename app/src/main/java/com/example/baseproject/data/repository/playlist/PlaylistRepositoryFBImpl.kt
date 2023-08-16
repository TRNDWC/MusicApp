package com.example.baseproject.data.repository.playlist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.baseproject.data.model.LibraryItem
import com.example.baseproject.data.relation.SongPlaylistCrossRef
import com.example.baseproject.utils.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.j256.ormlite.stmt.query.In


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
            database.reference.child("users").child(auth.uid!!).apply {
                child("playlists").setValue(list)
            }
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override fun getCrossRef(): MutableLiveData<Response<List<SongPlaylistCrossRef>>> {
        val playlistResponse = MutableLiveData<Response<List<SongPlaylistCrossRef>>>()
        var lists = mutableListOf<SongPlaylistCrossRef>()
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
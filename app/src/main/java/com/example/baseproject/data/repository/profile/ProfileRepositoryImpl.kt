package com.example.baseproject.data.repository.profile

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.example.baseproject.utils.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ProfileRepositoryImpl : ProfileRepository {
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()


    override suspend fun updateProfile(name: String, profilePictureUri: Uri?): Response<Boolean> {
        return try {
            database.reference.child("users").child(auth.uid!!).child("profile").apply {
                child("display_name").setValue(name)
            }
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override fun getProfile(): MutableLiveData<Response<String>> {
        val profileResponse = MutableLiveData<Response<String>>()
        database.reference.child("users").child(auth.uid!!).child("profile")
            .addValueEventListener(object : com.google.firebase.database.ValueEventListener {
                override fun onDataChange(snapshot: com.google.firebase.database.DataSnapshot) {
                    val name = snapshot.child("display_name").value.toString()
                    profileResponse.postValue(Response.Success(name))
                }

                override fun onCancelled(error: com.google.firebase.database.DatabaseError) {
                    profileResponse.postValue(Response.Failure(error.toException()))
                }
            })
        return profileResponse
    }
}
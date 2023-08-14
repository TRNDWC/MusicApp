package com.example.baseproject.data.repository.profile

import androidx.lifecycle.MutableLiveData
import com.example.baseproject.utils.Response

interface ProfileRepository {
    suspend fun updateProfile(name: String): Response<Boolean>
    fun getProfile(): MutableLiveData<Response<String>>
}
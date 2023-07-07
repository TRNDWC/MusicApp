package com.example.baseproject.ui.playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.base.BaseViewModel

class PlaylistViewModel : BaseViewModel() {
    private val _query = MutableLiveData<String>("")
    val mQuery: LiveData<String>
        get() = _query

    fun update(data: String){
        _query.value = data
    }


}
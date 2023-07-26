package com.example.baseproject.ui.play

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.base.BaseViewModel


class PlayViewModel  : BaseViewModel() {
    val songDescription : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}
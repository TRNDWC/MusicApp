package com.example.baseproject.ui.splash

import android.app.Activity
import android.os.Environment
import android.provider.MediaStore
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.baseproject.R
import com.example.baseproject.data.DataRepository
import com.example.baseproject.data.PlaylistSongItem
import com.example.core.base.BaseViewModel
import com.example.core.utils.SingleLiveEvent
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(data: DataRepository) : BaseViewModel() {

    val actionSPlash = SingleLiveEvent<SplashActionState>()
    val splashTitle = MutableLiveData(R.string.splash)
    var test = MutableLiveData<Int>()

    init {
        test.value = data.getData(File(Environment.getExternalStorageDirectory().getAbsolutePath())).size
        viewModelScope.launch {
            delay(5000)
            actionSPlash.value = SplashActionState.Finish
        }
    }
}

sealed class SplashActionState {
    object Finish : SplashActionState()
}
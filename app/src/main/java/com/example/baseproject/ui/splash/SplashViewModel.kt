package com.example.baseproject.ui.splash

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.baseproject.R
import com.example.baseproject.data.datarepo.DataRepository
import com.example.core.base.BaseViewModel
import com.example.core.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(data: DataRepository, application: Application) :
    BaseViewModel() {

    val actionSPlash = SingleLiveEvent<SplashActionState>()
    val splashTitle = MutableLiveData(R.string.splash)

    init {
        viewModelScope.launch {
            delay(1000)
            actionSPlash.value = SplashActionState.Finish
        }
    }
}

sealed class SplashActionState {
    object Finish : SplashActionState()
}
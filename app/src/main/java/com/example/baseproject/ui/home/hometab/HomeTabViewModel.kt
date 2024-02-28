package com.example.baseproject.ui.home.hometab

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.baseproject.ApiService.ApiService
import com.example.baseproject.data.model.ChartModel
import com.example.baseproject.data.model.User
import com.example.baseproject.data.repository.auth.AuthRepository
import com.example.baseproject.data.repository.profile.ProfileRepository
import com.example.baseproject.utils.Response
import com.example.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeTabViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository)
: BaseViewModel()
{
    private var _profileResponse = profileRepository.getProfile()
    val profileResponse: LiveData<Response<User>> = _profileResponse
    var chartUpdate = MutableLiveData<Response<ChartModel>>()

    fun getChart(){
        ApiService.create().getChart().enqueue(object : retrofit2.Callback<ChartModel>{
            override fun onResponse(call: retrofit2.Call<ChartModel>, response: retrofit2.Response<ChartModel>) {
                if (response.isSuccessful){
                    chartUpdate.postValue(Response.Success(response.body()!!))
                    Log.d("Chart", "onResponse: ${response.body()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<ChartModel>, t: Throwable) {
                chartUpdate.postValue(Response.Failure(t as Exception))
                Log.d("Chart", "onFailure: ${t.message}")
            }
        })
    }

}
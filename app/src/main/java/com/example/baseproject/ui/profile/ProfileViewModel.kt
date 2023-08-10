package com.example.baseproject.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.baseproject.data.repository.auth.AuthRepository
import com.example.baseproject.data.repository.profile.ProfileRepository
import com.example.baseproject.utils.Response
import com.example.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository
) : BaseViewModel() {

    private var _logOutResponse = MutableLiveData<Response<Boolean>>()
    val logOutResponse: MutableLiveData<Response<Boolean>> get() = _logOutResponse

    fun logOut() {
        viewModelScope.launch {
            _logOutResponse.value = Response.Loading
            _logOutResponse.value = authRepository.firebaseLogout()
        }
    }

    private var _profileResponse = profileRepository.getProfile()
    val profileResponse: LiveData<Response<String>> = _profileResponse

    private var _editResponse = MutableLiveData<Response<Boolean>>()
    val editResponse: MutableLiveData<Response<Boolean>> get() = _editResponse

    fun updateProfile(name: String) {
        viewModelScope.launch {
            _editResponse.postValue(Response.Loading)
            _editResponse.postValue(profileRepository.updateProfile(name))
        }
    }

    private var _validator: MutableLiveData<Boolean> = MutableLiveData()
    val validator: LiveData<Boolean> get() = _validator

    private var _isValidName = false
    fun setValidState(
        isValidName: Boolean? = _isValidName
    ) {
        _isValidName = isValidName!!
        _validator.value = _isValidName
    }
}

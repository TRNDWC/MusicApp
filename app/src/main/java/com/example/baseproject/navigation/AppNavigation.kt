package com.example.baseproject.navigation

import android.os.Bundle
import com.example.core.model.network.navigationComponent.BaseNavigator

interface AppNavigation : BaseNavigator {

    fun openSplashToLogin(bundle: Bundle? = null)
    fun openLogintoHomeScreen(bundle: Bundle? = null)
    fun openLogintoSignUp(bundle: Bundle? = null)
    fun openSignUptoLogin(bundle: Bundle? = null)
    fun openHomeScreentoPlaylistScreen(bundle: Bundle? = null)
}
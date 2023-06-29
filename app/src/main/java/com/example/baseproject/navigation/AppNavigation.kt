package com.example.baseproject.navigation

import android.os.Bundle
import com.example.core.model.network.navigationComponent.BaseNavigator

interface AppNavigation : BaseNavigator {

    fun openSplashToHomeScreen(bundle: Bundle? = null)
}
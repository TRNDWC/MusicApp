package com.example.baseproject.navigation

import android.os.Bundle
import com.example.baseproject.R
import com.example.core.model.network.navigationComponent.BaseNavigatorImpl
import com.example.setting.DemoNavigation
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class AppNavigatorImpl @Inject constructor() : BaseNavigatorImpl(),
    AppNavigation, DemoNavigation {

    override fun openSplashToLogin(bundle: Bundle?) {
        openScreen(R.id.action_splashFragment_to_loginFragment, bundle)
    }

    override fun openLogintoHomeScreen(bundle: Bundle?) {
        openScreen(R.id.action_loginFragment_to_homeFragment, bundle)
    }

    override fun openLogintoSignUp(bundle: Bundle?) {
        openScreen(R.id.action_loginFragment_to_signupFragment, bundle)
    }

    override fun openSignUptoLogin(bundle: Bundle?) {
        openScreen(R.id.action_signupFragment_to_homeFragment, bundle)
    }


    override fun openDemoViewPager(bundle: Bundle?) {
        openScreen(R.id.action_splashFragment_to_loginFragment, bundle)
    }

}
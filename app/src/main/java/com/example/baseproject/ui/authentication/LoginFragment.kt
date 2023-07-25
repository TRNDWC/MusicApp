package com.example.baseproject.ui.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.baseproject.R
import com.example.baseproject.databinding.FragmentLoginBinding
import com.example.baseproject.navigation.AppNavigation
import com.example.core.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(R.layout.fragment_login) {

    companion object {
        fun newInstance() = LoginFragment()
    }
    @Inject
    lateinit var appNavigation: AppNavigation
    private val viewModel: LoginViewModel by viewModels()
    override fun getVM() = viewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mLogin : FragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)
        mLogin.loginTextView.setOnClickListener {
            appNavigation.openLogintoHomeScreen()
        }
        mLogin.freeSignUpButton.setOnClickListener{
            appNavigation.openLogintoSignUp()
        }
        return mLogin.root
    }
}
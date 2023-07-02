package com.example.baseproject.ui.authentication

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.example.baseproject.R
import com.example.baseproject.databinding.FragmentLoginBinding
import com.example.baseproject.navigation.AppNavigation
import com.example.baseproject.ui.home.HomeFragment
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
    ): View? {
        val mLogin : FragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)
        mLogin.loginTextView.setOnClickListener {
            appNavigation.openLogintoHomeScreen()
        }
        mLogin.freeSignUpButton.setOnClickListener{
            appNavigation.openLogintoSignUp()
        }
        return mLogin.root
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
//        // TODO: Use the ViewModel
//    }
}
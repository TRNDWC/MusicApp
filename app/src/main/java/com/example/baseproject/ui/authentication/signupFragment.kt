package com.example.baseproject.ui.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.baseproject.R
import com.example.baseproject.databinding.FragmentSignupBinding
import com.example.baseproject.navigation.AppNavigation
import com.example.core.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class signupFragment : BaseFragment<FragmentSignupBinding, SignupViewModel>(R.layout.fragment_signup) {

    companion object {
        fun newInstance() = signupFragment()
    }

    @Inject
    lateinit var appNavigation: AppNavigation
    private val viewModel: SignupViewModel by viewModels()
    override fun getVM() = viewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mSignup : FragmentSignupBinding = FragmentSignupBinding.inflate(inflater, container, false)
        mSignup.buttonNext.setOnClickListener {
            appNavigation.openSignUptoHome()
        }
        return mSignup.root
    }
}
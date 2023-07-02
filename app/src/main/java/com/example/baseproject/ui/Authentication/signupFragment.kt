package com.example.baseproject.ui.Authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.baseproject.R
import com.example.baseproject.databinding.FragmentLoginBinding
import com.example.baseproject.databinding.FragmentSignupBinding
import com.example.core.base.BaseFragment

class signupFragment : BaseFragment<FragmentSignupBinding,SignupViewModel>(R.layout.fragment_signup) {

    companion object {
        fun newInstance() = signupFragment()
    }

    private val viewModel: SignupViewModel by viewModels()
    override fun getVM() = viewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mSignup : FragmentSignupBinding = FragmentSignupBinding.inflate(inflater, container, false)
        mSignup.buttonNext.setOnClickListener {
            findNavController().navigate(R.id.action_signupFragment_to_homeFragment)
        }
        return mSignup.root
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(SignupViewModel::class.java)
//        // TODO: Use the ViewModel
//    }

}
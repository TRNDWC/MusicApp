package com.example.baseproject.ui.profile

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.example.baseproject.R
import com.example.baseproject.databinding.FragmentProfileBinding
import com.example.baseproject.navigation.AppNavigation
import com.example.baseproject.utils.Response
import com.example.core.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment :
    BaseFragment<FragmentProfileBinding, ProfileViewModel>(R.layout.fragment_profile) {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    @Inject
    lateinit var appNavigation: AppNavigation

    private val viewModel: ProfileViewModel by viewModels()
    override fun getVM(): ProfileViewModel = viewModel


    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        viewModel.profileResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Failure -> {}
                is Response.Loading -> {}
                is Response.Success -> {
                    binding.userName.text = response.data
                }
            }

        }
    }

    override fun bindingAction() {
        super.bindingAction()
        viewModel.logOutResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Failure -> {}
                is Response.Loading -> {}
                is Response.Success -> {
                    appNavigation.openProfileToStartScreen()
                }
            }
        }
    }

    override fun setOnClick() {
        super.setOnClick()
        binding.apply {
            btnLogout.setOnClickListener {
                viewModel.logOut()
            }
            btnEditProfile.setOnClickListener{
                EditProfileDialog(binding.userName.text.toString()).show(childFragmentManager, "EditProfileDialog")
            }
        }
    }

}
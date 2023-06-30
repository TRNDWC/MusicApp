package com.example.baseproject.ui.home.tab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.baseproject.R
import com.example.baseproject.databinding.FragmentHomeTabBinding
import com.example.core.base.BaseFragment

class HomeTabFragment : BaseFragment<FragmentHomeTabBinding,HomeTabViewModel>(R.layout.fragment_home_tab) {

//    companion object {
//        fun newInstance() = HomeTabFragment()
//    }

    private lateinit var viewModel: HomeTabViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_tab, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeTabViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
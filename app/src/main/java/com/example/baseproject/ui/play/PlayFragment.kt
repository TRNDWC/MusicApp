package com.example.baseproject.ui.play

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.baseproject.R
import com.example.baseproject.databinding.FragmentPlayBinding
import com.example.baseproject.databinding.FragmentSearchBinding
import com.example.baseproject.ui.search.SearchViewModel
import com.example.core.base.BaseFragment
import timber.log.Timber
import java.util.Timer

class PlayFragment() : BaseFragment<FragmentPlayBinding, PlayViewModel>(R.layout.fragment_play) {

    private var mPlayFragment: FragmentPlayBinding? = null

    private val viewModel: PlayViewModel by viewModels()
    override fun getVM() = viewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mPlayFragment = FragmentPlayBinding.inflate(inflater, container, false)
        val description: String = arguments?.getString("title").toString() +
                "\n" + arguments?.getString("artist").toString()
        mPlayFragment!!.songDes.text = description
        return mPlayFragment!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
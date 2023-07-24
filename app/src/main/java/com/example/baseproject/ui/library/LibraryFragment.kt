package com.example.baseproject.ui.library

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baseproject.R
import com.example.baseproject.data.LibraryItem
import com.example.baseproject.databinding.FragmentLibraryBinding
import com.example.baseproject.navigation.AppNavigation
import com.example.baseproject.navigation.ItemClickNavigation
import com.example.core.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LibraryFragment :
    BaseFragment<FragmentLibraryBinding, LibraryViewModel>(R.layout.fragment_library),
    ItemClickNavigation {

    private val viewModel: LibraryViewModel by viewModels()

    @Inject
    lateinit var appNavigation: AppNavigation
    override fun getVM(): LibraryViewModel = viewModel

    private var playlistList = listOf<LibraryItem>()

    override fun bindingStateView() {
        super.bindingStateView()

        viewModel.playlistList.observe(viewLifecycleOwner) { newList ->
            if (newList != null) {
                if (newList.isEmpty()) viewModel.addPlaylist(LibraryItem(0, "All Yours Songs", "1"))
                binding.libraryRcv.adapter = LibraryItemAdapter(newList, this)
                playlistList = newList
            }
        }
        binding.libraryRcv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    override fun onItemClick(position: Int) {
        val bundle = Bundle()
        bundle.putParcelable("playlist", playlistList[position])
        this.findNavController().navigate(R.id.action_libraryFragment_to_playlistFragment, bundle)
    }
}
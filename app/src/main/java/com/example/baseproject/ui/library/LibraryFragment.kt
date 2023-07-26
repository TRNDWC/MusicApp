package com.example.baseproject.ui.library

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baseproject.R
import com.example.baseproject.data.model.LibraryItem
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

    @Inject
    lateinit var appNavigation: AppNavigation
    private val viewModel: LibraryViewModel by activityViewModels()
    override fun getVM(): LibraryViewModel = viewModel
    private var playlistList = listOf<LibraryItem>()
    override fun setOnClick() {
        super.setOnClick()
        binding.addItem.setOnClickListener {
            AddPlaylistDialog().show(requireActivity().supportFragmentManager, "add_playlist")
        }
    }

    override fun bindingStateView() {
        super.bindingStateView()

        viewModel.playlistList.observe(viewLifecycleOwner) { newList ->
            binding.libraryRcv.adapter = LibraryItemAdapter(newList, this)
            playlistList = newList
        }

        viewModel.newPlaylist.observe(viewLifecycleOwner) { newList ->
            if (newList != "") {
                viewModel.addPlaylist(LibraryItem(0, newList.toString(), "1"))
                viewModel.set()
            }

            binding.libraryRcv.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onItemClick(position: Int) {
        val bundle = Bundle()
        bundle.putParcelable("playlist", playlistList[position])
        this.findNavController().navigate(R.id.action_libraryFragment_to_playlistFragment, bundle)
    }
}
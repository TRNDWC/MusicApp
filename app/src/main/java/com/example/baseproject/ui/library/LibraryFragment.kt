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

    private var playlistList = LibraryItemList()

    override fun bindingStateView() {
        super.bindingStateView()
        val playlistList = LibraryItemList()
        binding.libraryRcv.adapter = LibraryItemAdapter(playlistList, this)
        binding.libraryRcv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun LibraryItemList(): MutableList<LibraryItem> {
        val LibraryItemList: MutableList<LibraryItem> = ArrayList()
        LibraryItemList.add(LibraryItem("1", "Card 1"))
        LibraryItemList.add(LibraryItem("2", "Card 2"))
        LibraryItemList.add(LibraryItem("3", "Card 3"))
        LibraryItemList.add(LibraryItem("4", "Card 4"))
        return LibraryItemList
    }

    override fun onItemClick(position: Int) {
        val bundle = Bundle()
        bundle.putParcelable("playlist", playlistList[position])

        this.findNavController().navigate(R.id.action_libraryFragment_to_playlistFragment, bundle)

    }
}
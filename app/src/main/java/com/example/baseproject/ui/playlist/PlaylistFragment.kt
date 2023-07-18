package com.example.baseproject.ui.playlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baseproject.R
import com.example.baseproject.databinding.FragmentPlaylistBinding
import com.example.baseproject.navigation.AppNavigation
import com.example.baseproject.navigation.ItemClickNavigation
import com.example.baseproject.ui.library.LibraryItem
import com.example.core.base.BaseFragment
import com.example.core.utils.toast
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PlaylistFragment :
    BaseFragment<FragmentPlaylistBinding, PlaylistViewModel>(R.layout.fragment_playlist) {
    @Inject
    lateinit var appNavigation: AppNavigation
    private val viewModel: PlaylistViewModel by viewModels()
    override fun getVM() = viewModel
    private var msongList = listOf<PlaylistSongItem>()
    private var playlistAdapter = PlaylistSongItemAdapter(msongList)
    private lateinit var materialToolbar: MaterialToolbar
    private lateinit var actionButton: FloatingActionButton

    override fun setOnClick() {
        super.setOnClick()
        playlistAdapter.setOnItemClickListener(object : ItemClickNavigation {
            override fun onItemClick(position: Int) {
                val bundle = Bundle()
             bundle.putParcelable("songItem", msongList[position])
                appNavigation.openPlaylistScreentoPlayScreen(bundle)
            }
        })
        actionButton = binding.btnPlaylistPlay
        actionButton.setOnClickListener {
            viewModel.add(PlaylistSongItem(R.drawable.green_play_circle, "1", "1", R.raw.querry_qnt))
            playlistAdapter.setFilteredList(viewModel.songList.value!!)
            "add".toast(requireContext())
        }

        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                playlistAdapter.setFilteredList(viewModel.filter(newText))
                return true
            }
        })
    }

    override fun bindingStateView() {
        super.bindingStateView()
        materialToolbar = binding.materialToolbar
        actionButton = binding.btnPlaylistPlay
        val item = arguments?.getParcelable<LibraryItem>("playlist")

        materialToolbar.setTitle(item?.itemTitle)
        (activity as AppCompatActivity).setSupportActionBar(materialToolbar)
        binding.searchView.setBackgroundResource(R.color.color_btn)

        msongList = viewModel.songItemList()
        playlistAdapter.setFilteredList(msongList)
        binding.rcvPlaylistSong.adapter = playlistAdapter
        binding.rcvPlaylistSong.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        viewModel.songList.observe(viewLifecycleOwner) { newList ->
            playlistAdapter.setFilteredList(newList)
        }
    }
}
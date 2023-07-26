package com.example.baseproject.ui.playlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baseproject.R
import com.example.baseproject.data.model.LibraryItem
import com.example.baseproject.data.model.PlaylistSongItem
import com.example.baseproject.databinding.FragmentPlaylistBinding
import com.example.baseproject.navigation.AppNavigation
import com.example.baseproject.ui.playlist.addsong.AddSongDialog
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
    private var mSongList = listOf<PlaylistSongItem>()
    private var playlistAdapter = PlaylistSongItemAdapter(mSongList)
    private lateinit var materialToolbar: MaterialToolbar

    override fun setOnClick() {
        super.setOnClick()

        playlistAdapter.onItemClick = {
            val bundle = Bundle()
            bundle.putParcelable("songItem", it)
            this.findNavController().navigate(R.id.action_playlistFragment_to_playFragment, bundle)
        }

        searchAction()

        binding.addSong.setOnClickListener {
            AddSongDialog(arguments?.getParcelable<LibraryItem>("playlist")!!.playlistId).show(
                childFragmentManager,
                "add_song"
            )
        }
    }

    override fun bindingStateView() {
        super.bindingStateView()
        val item = arguments?.getParcelable<LibraryItem>("playlist")
        item?.let { viewModel.getSong(it.playlistId) }

        // material tool bar
        materialToolbar = binding.materialToolbar
        materialToolbar.title = item?.playlistTitle
        (activity as AppCompatActivity).setSupportActionBar(materialToolbar)
        binding.searchView.setBackgroundResource(R.color.color_btn)
        recyclerviewAction()
    }

    private fun recyclerviewAction() {
        playlistAdapter = PlaylistSongItemAdapter(mSongList)
        binding.rcvPlaylistSong.adapter = playlistAdapter
        binding.rcvPlaylistSong.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        viewModel.songList.observe(viewLifecycleOwner) { newList ->
            if (newList != null) {
                playlistAdapter.setFilteredList(newList)
                mSongList = newList
            }
        }
    }

    private fun searchAction() {
        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                playlistAdapter.setFilteredList(viewModel.filter(newText, mSongList))
                return true
            }
        })
    }
}
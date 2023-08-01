package com.example.baseproject.ui.playlist

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baseproject.R
import com.example.baseproject.data.model.LibraryItem
import com.example.baseproject.data.model.PlaylistSongItem
import com.example.baseproject.databinding.FragmentPlaylistBinding
import com.example.baseproject.databinding.PlaylistSongItemBinding
import com.example.baseproject.navigation.AppNavigation
import com.example.baseproject.service.MusicService
import com.example.baseproject.ui.playlist.addsong.AddSongDialog
import com.example.core.base.BaseFragment
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class PlaylistFragment :
    BaseFragment<FragmentPlaylistBinding, PlaylistViewModel>(R.layout.fragment_playlist),
    OnItemClickListener {
    @Inject
    lateinit var appNavigation: AppNavigation
    private val viewModel: PlaylistViewModel by viewModels()

    override fun getVM() = viewModel
    private var musicService: MusicService? = null
    private lateinit var mSongList: List<PlaylistSongItem>
    private lateinit var playlistAdapter: PlaylistSongItemAdapter
    private lateinit var materialToolbar: MaterialToolbar
    private var isServiceConnected: Boolean = false
    private lateinit var intent: Intent
    private lateinit var bundle: Bundle
    private var firstInit: Boolean = false
    private var previousClickedSong: PlaylistSongItem =
        PlaylistSongItem(songId = 0, songImage = "", songTitle = "", artists = "", resource = "")
    private val mServiceConnection: ServiceConnection = object : ServiceConnection {

        override fun onServiceConnected(componentName: ComponentName?, iBinder: IBinder?) {
            Log.e("HoangDH", "Service Connected from PlayList")
            val myBinder: MusicService.MyBinder = iBinder as MusicService.MyBinder
            musicService = myBinder.getMyService()
            isServiceConnected = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isServiceConnected = false
        }

    }


    override fun setOnClick() {
        super.setOnClick()
        recyclerviewAction()
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
    }

    private fun prepareBundle(item: PlaylistSongItem) {
        bundle = Bundle()
        val position = viewModel.songList.value!!.indexOf(item)
        bundle.putInt("song_position", position)
        bundle.putParcelableArrayList("song_list",
            viewModel.songList.value?.let { it1 -> ArrayList(it1) })
        bundle.putParcelable("song_item", item)
        intent = Intent(context, MusicService::class.java)
        intent.putExtra("song_bundle", bundle)
    }

    private fun recyclerviewAction() {
        viewModel.songList.observe(viewLifecycleOwner) { newList ->
            mSongList = newList
            playlistAdapter = PlaylistSongItemAdapter(mSongList, this)
            binding.rcvPlaylistSong.adapter = playlistAdapter
            binding.rcvPlaylistSong.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
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

    override fun onItemClicked(item: PlaylistSongItem, view: PlaylistSongItemBinding) {

        Log.e("HoangDH", "itemClicked")
        prepareBundle(item)
        Log.e("HoangDH", "$previousClickedSong")

        if (!firstInit) {
            requireActivity().startService(intent)
            requireActivity().bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
            firstInit = true
            previousClickedSong = item
            Log.e("HoangDH", "$firstInit")
        } else if (previousClickedSong != item) {
            requireActivity().stopService(intent)
            requireActivity().unbindService(mServiceConnection)
            requireActivity().startService(intent)
            requireActivity().bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
            previousClickedSong = item
        }


    }
}
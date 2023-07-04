package com.example.baseproject.ui.playlist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.baseproject.R
import com.example.baseproject.databinding.FragmentPlaylistBinding
import com.example.core.base.BaseFragment

class PlaylistFragment : BaseFragment<FragmentPlaylistBinding, PlaylistViewModel>(R.layout.fragment_playlist) {

    private var mPlaylistFragment : FragmentPlaylistBinding? = null

    companion object {
        fun newInstance() = PlaylistFragment()
    }

    private val viewModel: PlaylistViewModel by viewModels()

    override fun getVM() = viewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mPlaylistFragment = FragmentPlaylistBinding.inflate(inflater, container, false)
        val rcvPlaylistSongItem : RecyclerView = mPlaylistFragment!!.rcvPlaylistSong
        val layoutManager = LinearLayoutManager(mPlaylistFragment!!.root.context, LinearLayoutManager.VERTICAL, false)
        val playlistAdapter = PlaylistSongItemAdapter(songItemList())

        rcvPlaylistSongItem.layoutManager = layoutManager
        rcvPlaylistSongItem.adapter = playlistAdapter

        return mPlaylistFragment!!.root
    }



    private fun songItemList() : MutableList<PlaylistSongItem>{
        val songItemList : MutableList<PlaylistSongItem> = ArrayList()
        for(i in 1..7){
            songItemList.add(PlaylistSongItem("Alone", "Alan Walker"))
        }
        return songItemList
    }

}
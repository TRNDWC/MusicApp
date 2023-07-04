package com.example.baseproject.ui.playlist

import android.annotation.SuppressLint
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
import com.example.baseproject.navigation.AppNavigation
import com.example.baseproject.navigation.ItemClickNavigation
import com.example.core.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PlaylistFragment : BaseFragment<FragmentPlaylistBinding, PlaylistViewModel>(R.layout.fragment_playlist) {

    private var mPlaylistFragment : FragmentPlaylistBinding? = null

    @Inject
    lateinit var appNavigation: AppNavigation

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
        playlistAdapter.setOnItemClickListener(object  : ItemClickNavigation{
            override fun onItemClick(position: Int) {
                appNavigation.openPlaylistScreentoPlayScreen()
            }
        })

        rcvPlaylistSongItem.layoutManager = layoutManager
        rcvPlaylistSongItem.adapter = playlistAdapter

        return mPlaylistFragment!!.root
    }

    private fun songItemList() : MutableList<PlaylistSongItem>{
        val songItemList : MutableList<PlaylistSongItem> = ArrayList()
            songItemList.add(PlaylistSongItem("Có ai hẹn hò cùng em chưa", "Quân AP"))
            songItemList.add(PlaylistSongItem("Đưa em về nhà", "GreyD, Chillies"))
            songItemList.add(PlaylistSongItem("Nếu lúc đó", "TLinh"))
        return songItemList
    }

}
package com.example.baseproject.ui.playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
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
class PlaylistFragment :
    BaseFragment<FragmentPlaylistBinding, PlaylistViewModel>(R.layout.fragment_playlist) {

    private var mPlaylistFragment: FragmentPlaylistBinding? = null

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
        mPlaylistFragment?.playlistDescription?.text = arguments?.getString("title").toString()
        val rcvPlaylistSongItem: RecyclerView = mPlaylistFragment!!.rcvPlaylistSong
        val layoutManager = LinearLayoutManager(
            mPlaylistFragment!!.root.context,
            LinearLayoutManager.VERTICAL,
            false
        )
        val songList = songItemList()
        val playlistAdapter = PlaylistSongItemAdapter(songList)

        playlistAdapter.setOnItemClickListener(object : ItemClickNavigation {
            override fun onItemClick(position: Int) {
                val bundle = Bundle()
                bundle.putString("title", songList[position].songTitle)
                bundle.putString("artist", songList[position].artists)
                bundle.putInt("song_image", songList[position].songImage)
                appNavigation.openPlaylistScreentoPlayScreen(bundle)
            }
        })

        rcvPlaylistSongItem.layoutManager = layoutManager
        rcvPlaylistSongItem.adapter = playlistAdapter

        return mPlaylistFragment!!.root
    }

    private fun songItemList(): MutableList<PlaylistSongItem> {
        var songItemList: MutableList<PlaylistSongItem> = ArrayList()
        songItemList.add(
            PlaylistSongItem(
                R.drawable.green_play_circle,
                "Có ai hẹn hò cùng em chưa",
                "Quân AP"
            )
        )
        songItemList.add(
            PlaylistSongItem(
                R.drawable.green_play_circle,
                "Đưa em về nhà",
                "GreyD, Chillies"
            )
        )
        songItemList.add(
            PlaylistSongItem(
                R.drawable.green_play_circle,
                "Nếu lúc đó",
                "TLinh"
            )
        )
        return songItemList
    }

}
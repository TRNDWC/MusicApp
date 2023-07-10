package com.example.baseproject.ui.playlist

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.size
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baseproject.R
import com.example.baseproject.databinding.FragmentPlaylistBinding
import com.example.baseproject.navigation.AppNavigation
import com.example.core.base.BaseFragment
import com.example.core.utils.toast
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class PlaylistFragment :
    BaseFragment<FragmentPlaylistBinding, PlaylistViewModel>(R.layout.fragment_playlist) {
    @Inject
    lateinit var appNavigation: AppNavigation
    private val viewModel: PlaylistViewModel by viewModels()
    override fun getVM() = viewModel
    val songList = songItemList1()
    private val playlistAdapter = PlaylistSongItemAdapter(songList)
    private lateinit var materialToolbar: MaterialToolbar
    private lateinit var actionButton : FloatingActionButton

    override fun setOnClick() {
        super.setOnClick()
        playlistAdapter.onItemClick = {
            val bundle = Bundle()
            bundle.putString("title", it.songTitle)
            bundle.putString("artist", it.artists)
            bundle.putInt("song_image", it.songImage)
            appNavigation.openPlaylistScreentoPlayScreen(bundle)
        }
    }

    override fun bindingStateView() {
        super.bindingStateView()
        materialToolbar = binding.materialToolbar
        materialToolbar.setTitle(arguments?.getString("title"))
        actionButton = binding.btnPlaylistPlay

        (activity as AppCompatActivity).setSupportActionBar(materialToolbar)
        binding.rcvPlaylistSong.adapter = playlistAdapter
        binding.rcvPlaylistSong.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.searchView.setBackgroundResource(R.color.color_btn)

        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filter(newText)
                return true
            }
        })
    }

    private fun filter(newText: String?) {
        if (newText != null) {
            val filterList = ArrayList<PlaylistSongItem>()
            for (i in songList){
                if (convert(i.songTitle).lowercase(Locale.ROOT).contains(convert(newText).lowercase())){
                    filterList.add(i)
                }
            }
            if (filterList.isEmpty()){
                val emptyList = ArrayList<PlaylistSongItem>()
                playlistAdapter.setFilteredList(emptyList)
            } else {
                playlistAdapter.setFilteredList(filterList)
            }
        }
    }
    private fun songItemList1(): MutableList<PlaylistSongItem> {
        val songItemList: MutableList<PlaylistSongItem> = ArrayList()
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

    fun convert(str: String): String {
        var str = str
        str = str.replace("à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ".toRegex(), "a")
        str = str.replace("è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ".toRegex(), "e")
        str = str.replace("ì|í|ị|ỉ|ĩ".toRegex(), "i")
        str = str.replace("ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ".toRegex(), "o")
        str = str.replace("ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ".toRegex(), "u")
        str = str.replace("ỳ|ý|ỵ|ỷ|ỹ".toRegex(), "y")
        str = str.replace("đ".toRegex(), "d")
        str = str.replace("À|Á|Ạ|Ả|Ã|Â|Ầ|Ấ|Ậ|Ẩ|Ẫ|Ă|Ằ|Ắ|Ặ|Ẳ|Ẵ".toRegex(), "A")
        str = str.replace("È|É|Ẹ|Ẻ|Ẽ|Ê|Ề|Ế|Ệ|Ể|Ễ".toRegex(), "E")
        str = str.replace("Ì|Í|Ị|Ỉ|Ĩ".toRegex(), "I")
        str = str.replace("Ò|Ó|Ọ|Ỏ|Õ|Ô|Ồ|Ố|Ộ|Ổ|Ỗ|Ơ|Ờ|Ớ|Ợ|Ở|Ỡ".toRegex(), "O")
        str = str.replace("Ù|Ú|Ụ|Ủ|Ũ|Ư|Ừ|Ứ|Ự|Ử|Ữ".toRegex(), "U")
        str = str.replace("Ỳ|Ý|Ỵ|Ỷ|Ỹ".toRegex(), "Y")
        str = str.replace("Đ".toRegex(), "D")
        return str
    }
}
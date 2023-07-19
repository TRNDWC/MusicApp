package com.example.baseproject.ui.library

import com.example.baseproject.R
import com.example.baseproject.data.PlaylistSongItem
import com.example.core.base.BaseViewModel

class LibraryViewModel : BaseViewModel() {
    fun songItemList(): List<PlaylistSongItem> {
        val songItemList = mutableListOf<PlaylistSongItem>()
        songItemList.add(
            PlaylistSongItem(
                0,
                R.drawable.green_play_circle,
                "Có ai hẹn hò cùng em chưa",
                "Quân AP",
                R.raw.co_ai_hen_ho_cung_em_chua_quan_ap
            )
        )
        songItemList.add(
            PlaylistSongItem(
                0,
                R.drawable.green_play_circle,
                "Đưa em về nhà",
                "GreyD, Chillies",
                R.raw.dua_em_ve_nha_greyd_chillies
            )
        )
        songItemList.add(
            PlaylistSongItem(
                0,
                R.drawable.green_play_circle,
                "Nếu lúc đó",
                "TLinh",
                R.raw.neu_luc_do_tlinh
            )
        )
        songItemList.add(
            PlaylistSongItem(
                0,
                R.drawable.green_play_circle,
                "Query",
                "QNT",
                R.raw.querry_qnt
            )
        )

        return songItemList
    }
}
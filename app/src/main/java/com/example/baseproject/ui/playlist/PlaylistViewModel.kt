package com.example.baseproject.ui.playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.baseproject.R
import com.example.core.base.BaseViewModel
import com.example.core.utils.toast
import java.util.Locale

class PlaylistViewModel : BaseViewModel() {
    private val _songList = MutableLiveData<MutableList<PlaylistSongItem>>()
    val songList: LiveData<MutableList<PlaylistSongItem>>
        get() = _songList

    fun add(song: PlaylistSongItem) {
        _songList.value?.add(song)
    }

    fun convert(str: String?): String {
        var str = str
        str = str!!.replace("à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ".toRegex(), "a")
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

    fun filter(newText: String?): List<PlaylistSongItem> {
        val filterList = ArrayList<PlaylistSongItem>()
        if (newText != null) {
            _songList.value?.forEach {
                if (convert(it.songTitle).lowercase(Locale.ROOT)
                        .contains(convert(newText).lowercase())
                ) {
                    filterList.add(it)
                }
            }
            if (filterList.isEmpty()) {
                val emptyList = ArrayList<PlaylistSongItem>()
                return emptyList
            } else {
                return filterList
            }
        }
        return songItemList()
    }

    fun songItemList(): MutableList<PlaylistSongItem> {
        val songItemList = mutableListOf<PlaylistSongItem>()
        songItemList.add(
            PlaylistSongItem(
                R.drawable.green_play_circle,
                "Có ai hẹn hò cùng em chưa",
                "Quân AP",
                R.raw.co_ai_hen_ho_cung_em_chua_quan_ap
            )
        )
        songItemList.add(
            PlaylistSongItem(
                R.drawable.green_play_circle,
                "Đưa em về nhà",
                "GreyD, Chillies",
                R.raw.dua_em_ve_nha_greyd_chillies
            )
        )
        songItemList.add(
            PlaylistSongItem(
                R.drawable.green_play_circle,
                "Nếu lúc đó",
                "TLinh",
                R.raw.neu_luc_do_tlinh
            )
        )
        songItemList.add(
            PlaylistSongItem(
                R.drawable.green_play_circle,
                "Querry",
                "QNT",
                R.raw.querry_qnt
            )
        )

        _songList.value = songItemList
        return songItemList
    }
}
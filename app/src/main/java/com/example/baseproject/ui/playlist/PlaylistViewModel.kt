package com.example.baseproject.ui.playlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.baseproject.data.AllSong
import com.example.baseproject.data.PlaylistSongItem
import com.example.baseproject.data.SongDatabase
import com.example.baseproject.data.SongRepository
import com.example.core.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class PlaylistViewModel @Inject constructor(
    application: Application
) : BaseViewModel() {
    val songList: LiveData<List<PlaylistSongItem>>
    private val repository: SongRepository

    init {
        val songDao = SongDatabase.getDatabase(application).songDao()
        repository = SongRepository(songDao)
        songList = repository.getAllSong
    }

    fun addSong(item: PlaylistSongItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addSong(item)
        }
    }



    fun convert(stri: String): String {
        var str = stri
        str = str.replace("[àáạảãâầấậẩẫăằắặẳẵ]".toRegex(), "a")
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
            songList.value?.forEach {
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
        return songList.value!!.toList()
    }
}
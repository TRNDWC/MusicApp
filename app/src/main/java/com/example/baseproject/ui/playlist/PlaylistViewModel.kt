package com.example.baseproject.ui.playlist

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.baseproject.data.MusicDatabase
import com.example.baseproject.data.MusicRepository
import com.example.baseproject.data.model.LibraryItem
import com.example.baseproject.data.model.PlaylistSongItem
import com.example.baseproject.data.relation.SongPlaylistCrossRef
import com.example.core.base.BaseViewModel
import com.example.core.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    application: Application
) : BaseViewModel() {
    private val repository: MusicRepository

    init {
        val musicDao = MusicDatabase.getDatabase(application).musicDao()
        repository = MusicRepository(musicDao)
    }

    // hiển thị danh sách các bài hát thuộc playlist có id là id

    private var _songList = MutableLiveData<List<PlaylistSongItem>>()
    var songList: LiveData<List<PlaylistSongItem>> = _songList

    fun getSong(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _songList.postValue(repository.getSongsOfPlaylist(id).songs)
            _songList.value?.forEach {
                Log.d("playlist and song", "${id} + +${it.songTitle}")
            }
        }
    }

    fun addSongtoPlaylist(songId: Int, playlistId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addSongPlaylistCrossRef(SongPlaylistCrossRef(songId, playlistId))
        }
    }


    // hiển thị các bài hát để thêm vào playlist hiện tại

    val addSongList = MutableLiveData<List<PlaylistSongItem>>()


    fun listAll() {
        repository.getAllSong()
            .flowOn(Dispatchers.IO)
            .onStart {
                isLoading.value = true
            }.onCompletion {
                isLoading.value = false
            }.onEach {

                addSongList.value = addDialogFilter(it)
            }.catch {
                messageError.value = it.message
            }.launchIn(viewModelScope)
    }

    fun addDialogFilter(list: List<PlaylistSongItem>): List<PlaylistSongItem> {
        val data = mutableListOf<Int>()
        songList.value?.forEach {
            data.add(it.songId)
        }
        val result = mutableListOf<PlaylistSongItem>()
        list.forEach {
            if (!data.contains(it.songId)) {
                result.add(it)
                Log.d("checked", it.songTitle.toString())
            }
        }
        return result
    }

    // play fragment dialog
    val tPlaylistListId = SingleLiveEvent<List<Int>>()

    fun getPlaylistOfSong(id: Int) {
        repository.getPlaylistsOfSong(id)
            .flowOn(Dispatchers.IO)
            .onStart {
                isLoading.value = true
            }.onCompletion {
                isLoading.value = false
            }.onEach {
                tPlaylistListId.value = filter(it.playlists)
            }.catch {
                messageError.value = it.message
            }.launchIn(viewModelScope)
    }

    fun filter(list: List<LibraryItem>): List<Int> {
        val dataId: MutableList<Int> = mutableListOf()
        list.forEach {
            dataId.add(it.playlistId)
        }
        return dataId
    }

    // custom fragment

    fun reset(newList: List<Int>, oldList: List<Int>, songId: Int) {
//        viewModelScope.launch {
            newList.forEach {
                if (it !in oldList) {
                    viewModelScope.launch(Dispatchers.IO) {
                        repository.addSongPlaylistCrossRef(SongPlaylistCrossRef(songId, it))
                    }

                }
            }

            oldList.forEach {
                if (it !in newList){
                    viewModelScope.launch(Dispatchers.IO) {
                        repository.deleteSongPlaylistCrossRef(SongPlaylistCrossRef(songId, it))
                    }
                }

            }
//        }
    }

    val playlistId = MutableLiveData<Int>()
    fun set(id: Int) {
        playlistId.value = id

    }

    // lấy dữ liệu về các playlist

    val playlists = SingleLiveEvent<List<LibraryItem>>()
    fun getAllPlaylists() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.getAllPlaylist()
            playlists.postValue(data)
        }
    }

    // các chức năng khác
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

    fun filter(newText: String?, list: List<PlaylistSongItem>): List<PlaylistSongItem> {
        val filterList = ArrayList<PlaylistSongItem>()
        if (newText != null) {
            list.forEach {
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
        return filterList
    }

}
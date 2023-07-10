package com.example.baseproject.ui.playlist

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class PlaylistSongItem(
    var songImage: Int,
    var songTitle: String?,
    var artists: String?,
    var resource: Int
    ) : Serializable
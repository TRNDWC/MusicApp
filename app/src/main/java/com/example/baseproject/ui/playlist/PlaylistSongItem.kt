package com.example.baseproject.ui.playlist

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class PlaylistSongItem(
    var songImage: Int,
    var songTitle: String?,
    var artists: String?,
    var resource: Int
    ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(songImage)
        parcel.writeString(songTitle)
        parcel.writeString(artists)
        parcel.writeInt(resource)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PlaylistSongItem> {
        override fun createFromParcel(parcel: Parcel): PlaylistSongItem {
            return PlaylistSongItem(parcel)
        }

        override fun newArray(size: Int): Array<PlaylistSongItem?> {
            return arrayOfNulls(size)
        }
    }
}
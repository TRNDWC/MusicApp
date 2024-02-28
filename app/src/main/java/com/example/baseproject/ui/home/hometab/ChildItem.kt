package com.example.baseproject.ui.home.hometab

import android.os.Parcel
import android.os.Parcelable
import com.example.baseproject.data.model.PlaylistSongItem

class ChildItem(
    val childItemTitle: String?,
    val childItemImage: String?,
    val data: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(childItemTitle)
        parcel.writeString(childItemImage)
        parcel.writeString(data)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ChildItem> {
        override fun createFromParcel(parcel: Parcel): ChildItem {
            return ChildItem(parcel)
        }

        override fun newArray(size: Int): Array<ChildItem?> {
            return arrayOfNulls(size)
        }
    }

    fun toPlaylistSongItem(): PlaylistSongItem {
        return PlaylistSongItem(
            songImage = childItemImage,
            songTitle = childItemTitle?.substringBefore("\n"),
            artists = childItemTitle?.substringAfter("\n"),
            resource = data
        )
    }

    fun toLibraryItem(): com.example.baseproject.data.model.LibraryItem {
        return com.example.baseproject.data.model.LibraryItem(
            playlistId = data!!,
            playlistTitle = childItemTitle,
            playlistImage = childItemImage,

        )
    }

    override fun toString(): String {
        return "ChildItem(childItemTitle=$childItemTitle, childItemImage=$childItemImage, data=$data)"
    }
}
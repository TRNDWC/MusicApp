package com.example.baseproject.data

import android.media.Image
import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "song_data")
data class PlaylistSongItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var songImage: Int,
    var songTitle: String?,
    var artists: String?,
    var resource : Int
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
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
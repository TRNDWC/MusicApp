package com.example.baseproject.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlist_data")
class LibraryItem(
    @PrimaryKey(autoGenerate = true)
    val playlistId: Int,
    val playlistTitle: String?,
    val playlistImage: String?
) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(playlistId)
        dest.writeString(playlistTitle)
        dest.writeString(playlistImage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LibraryItem> {
        override fun createFromParcel(parcel: Parcel): LibraryItem {
            return LibraryItem(parcel)
        }

        override fun newArray(size: Int): Array<LibraryItem?> {
            return arrayOfNulls(size)
        }
    }
}


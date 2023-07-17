package com.example.baseproject.data;
import android.os.Parcel
import android.os.Parcelable

class LibraryItem(val id: String?, val itemTitle: String?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(itemTitle)
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

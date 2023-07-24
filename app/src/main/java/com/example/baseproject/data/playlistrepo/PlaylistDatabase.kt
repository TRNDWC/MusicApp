package com.example.baseproject.data.playlistrepo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.baseproject.data.model.LibraryItem

@Database(entities = [LibraryItem::class], version = 1, exportSchema = false)
abstract class PlaylistDatabase : RoomDatabase() {
    abstract fun playlistDao(): PlaylistDao

    companion object {
        @Volatile
        private var INSTANCE: PlaylistDatabase? = null
        fun getDatabase(context: Context): PlaylistDatabase {
            val temp = INSTANCE
            if (temp != null)
                return temp
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlaylistDatabase::class.java,
                    "playlist_data"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
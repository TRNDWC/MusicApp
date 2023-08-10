package com.example.baseproject.data.songrepo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.baseproject.data.model.PlaylistSongItem

@Database(entities = [PlaylistSongItem::class], version = 1, exportSchema = false)
abstract class SongDatabase : RoomDatabase() {
    abstract fun songDao(): SongDao

    companion object {
        @Volatile
        private var INSTANCE: SongDatabase? = null
        fun getDatabase(context: Context): SongDatabase {
            val temp = INSTANCE
            if (temp != null)
                return temp
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SongDatabase::class.java,
                    "song_data"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
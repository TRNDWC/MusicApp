package com.example.baseproject.data

import android.content.Context
import android.provider.MediaStore
import android.util.Log
import com.example.baseproject.R
import dagger.hilt.android.scopes.ActivityScoped
import java.io.File
import javax.inject.Inject

@ActivityScoped
class DataRepositoryImpl @Inject constructor() : DataRepository {
    override fun getData(file: File): List<File> {
        val musicFile = mutableListOf<File>()
        file.listFiles()?.let {
            for (currentFile in it) {
                Log.d("file from storage", currentFile.name.toString())
                if (currentFile.isDirectory && !currentFile.isHidden) {
                    musicFile.addAll(getData(currentFile))
                } else {
                    if (currentFile.name.endsWith(".mp3")) {
                        musicFile.add(currentFile)
                    }
                }
            }
        }
        return musicFile
    }

    override fun getSong(context: Context): List<PlaylistSongItem> {
        val tempAudioList: MutableList<PlaylistSongItem> = ArrayList<PlaylistSongItem>()
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.AudioColumns.DATA,
            MediaStore.Audio.AudioColumns.TITLE,
            MediaStore.Audio.ArtistColumns.ARTIST
        )
        val c = context.contentResolver.query(
            uri,
            projection,
            null,
            null,
            null
        )

        if (c != null) {
            while (c.moveToNext()) {
                val path = c.getString(0)
                val name = c.getString(1)
                val artist = c.getString(2)
                val audioModel = PlaylistSongItem(0, R.drawable.green_play_circle, name, artist)
                Log.d("Name :$name", "1`23456789")
                Log.d("Path :$path", " Artist :$artist")
                tempAudioList.add(audioModel)
            }
            c.close()
        }
        return tempAudioList
    }
}

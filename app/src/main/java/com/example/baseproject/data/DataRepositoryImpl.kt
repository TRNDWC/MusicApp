package com.example.baseproject.data

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.example.baseproject.R
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject


@ActivityScoped
class DataRepositoryImpl @Inject constructor() : DataRepository {

    override fun getSong(context: Context): List<PlaylistSongItem> {
        val musicList = mutableListOf<PlaylistSongItem>()
        val musicResolver: ContentResolver = context.contentResolver
        val musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val musicCursor = musicResolver.query(musicUri, null, null, null, null)
        if (musicCursor != null && musicCursor.moveToFirst()) {
            //get columns
            val titleColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val idColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID)
            val albumID = musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)
            val artistColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            val songLink = musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA)
            //add songs to list
            do {
                val thisId = musicCursor.getInt(idColumn)
                val thisTitle = musicCursor.getString(titleColumn)
                val thisArtist = musicCursor.getString(artistColumn)
                val thisSongLink = Uri.parse(musicCursor.getString(songLink))
//                val some = musicCursor.getLong(albumID)
//                val uri = ContentUris.withAppendedId(sArtworkUri, some)
                if (thisSongLink.toString().endsWith(".mp3"))
                    musicList.add(
                        PlaylistSongItem(
                            thisId,
                            R.drawable.green_play_circle,
                            thisTitle,
                            thisArtist,
                            thisSongLink.toString()
                        )
                    )
                Log.d("title", thisSongLink.toString())
            } while (musicCursor.moveToNext())
            assert(musicCursor != null)
            musicCursor.close()
        }
        return musicList
    }
}

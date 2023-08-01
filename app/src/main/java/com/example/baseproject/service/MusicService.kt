package com.example.baseproject.service

import android.app.PendingIntent
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.provider.MediaStore
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.baseproject.BaseApplication.Companion.CHANNEL_ID
import com.example.baseproject.R
import com.example.baseproject.data.model.PlaylistSongItem
import com.example.baseproject.ui.play.PlayFragmentDialog
import com.example.core.base.BaseService


class MusicService : BaseService() {

    private lateinit var musicPlayer: MediaPlayer
    private var binder = MyBinder()
    private lateinit var mediaSession : MediaSessionCompat
    private lateinit var songItem: PlaylistSongItem

    private val _songLiveData = MutableLiveData<PlaylistSongItem>()
    val songLiveData: LiveData<PlaylistSongItem> = _songLiveData
    lateinit var songList: List<PlaylistSongItem>
    var songPosition : Int = 0
    inner class MyBinder : Binder() {
        fun getMyService(): MusicService = this@MusicService
    }

    override fun onCreate() {
        super.onCreate()
        mediaSession = MediaSessionCompat(this,"PlayerAudio")
        Log.e("HoangDH", "onCreate")
    }

    override fun onBind(intent: Intent?): IBinder {
        Log.e("HoangDH", "onBind")
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e("HoangDH", "onStartCommand")
        val bundle = intent!!.getBundleExtra("song_bundle")
        songItem = bundle!!.getParcelable("song_item")!!

        songList = bundle.getParcelableArrayList("song_list")!!
        songPosition = bundle.getInt("song_position")
        musicPlayer = MediaPlayer.create(this, songItem.resource?.toUri())
        _songLiveData.postValue(songItem)
        startMusic(songItem)
        sendNotification(songItem)
        return START_NOT_STICKY
    }


    override fun onUnbind(intent: Intent?): Boolean {
        Log.e("HoangDH", "onUnbind")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        musicPlayer.stop()
    }

    fun startMusic(songItem: PlaylistSongItem) {
        Log.e("HoangDH", "startMusic")
        musicPlayer.start()
    }

    fun pauseMusic() {
        Log.e("HoangDH", "pauseMusic")
        musicPlayer.pause()
    }


    fun isPlaying(): Boolean {
        return musicPlayer.isPlaying
    }

    fun currentPosition(): Int {
        return musicPlayer.currentPosition
    }

    fun duration(): Int {
        return musicPlayer.duration
    }

    fun seekTo(progress: Int) {
        musicPlayer.seekTo(progress)
    }

    private fun sendNotification(songItem: PlaylistSongItem) {
        val intent = Intent(this, PlayFragmentDialog::class.java)
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(
                applicationContext,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )

        val picture = MediaStore.Images.Media.getBitmap(this.contentResolver, songItem.songImage?.toUri())


        val notification =
            NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.spotify)
                .setLargeIcon(picture)
                .setContentTitle(songItem.songTitle)
                .setContentText(songItem.artists)
                .addAction(R.drawable.ic_pre,"Previous",null)
                .addAction(R.drawable.ic_play,"Play",null)
                .addAction(R.drawable.ic_next,"Next",null)
                .setStyle(androidx.media.app.NotificationCompat.MediaStyle()
                    .setMediaSession(mediaSession.sessionToken))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setOnlyAlertOnce(true)
                .build()
        startForeground(1, notification)
    }

}


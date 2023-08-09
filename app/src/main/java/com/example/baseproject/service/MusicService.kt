package com.example.baseproject.service

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Bundle
import android.os.IBinder
import android.provider.MediaStore
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDeepLinkBuilder
import com.example.baseproject.BaseApplication.Companion.CHANNEL_ID
import com.example.baseproject.R
import com.example.baseproject.data.model.PlaylistSongItem
import com.example.baseproject.ui.play.PlayFragmentDialog
import com.example.core.base.BaseService


class MusicService : BaseService() {

    private lateinit var musicPlayer: MediaPlayer
    private var binder = MyBinder()
    private lateinit var mediaSession: MediaSessionCompat
    private lateinit var songItem: PlaylistSongItem
    private var isLooping: Boolean = false

    private val _songLiveData = MutableLiveData<PlaylistSongItem>()
    val songLiveData: LiveData<PlaylistSongItem> = _songLiveData
    private val _songIsPlaying: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val songIsPlaying: LiveData<Boolean> = _songIsPlaying
    lateinit var songList: List<PlaylistSongItem>
    var songPosition: Int = 0

    companion object {
        const val NEED_OPEN_DIALOG = "need_open_dialog"
    }

    inner class MyBinder : Binder() {
        fun getMyService(): MusicService = this@MusicService
    }

    override fun onCreate() {
        super.onCreate()
        mediaSession = MediaSessionCompat(this, "PlayerAudio")
        Log.e("HoangDH", "onCreate")
    }

    override fun onBind(intent: Intent?): IBinder {
        Log.e("HoangDH", "onBind")
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e("HoangDH", "onStartCommand")
        val bundle = intent?.getBundleExtra("song_bundle")
        songItem = bundle!!.getParcelable("song_item")!!
        songList = bundle.getParcelableArrayList("song_list")!!
        songPosition = bundle.getInt("song_position")
        _songLiveData.postValue(songItem)
        musicPlayer = MediaPlayer.create(this, songItem.resource?.toUri())
        startMusic()
        sendNotification(songItem)

//        val musicAction = intent.action
//        handleMusicAction(musicAction!!)
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

    fun startMusic() {
        Log.e("HoangDH", "startMusic")
        musicPlayer.start()
        _songIsPlaying.postValue(true)
        if (isLooping) {
            if (songPosition < songList.size - 1) {
                musicPlayer.setOnCompletionListener {
                    songPosition++
                    val nextSong = songList[songPosition]
                    _songLiveData.postValue(nextSong)
                    sendNotification(nextSong)
                    autoPlayNextSong(nextSong)
                }
            } else {
                musicPlayer.setOnCompletionListener {
                    songPosition = 0
                    val nextSong = songList[songPosition]
                    _songLiveData.postValue(nextSong)
                    sendNotification(nextSong)
                    autoPlayNextSong(nextSong)
                }
            }
        } else {
            if (songPosition < songList.size - 1) {
                musicPlayer.setOnCompletionListener {
                    songPosition++
                    val nextSong = songList[songPosition]
                    _songLiveData.postValue(nextSong)
                    sendNotification(nextSong)
                    autoPlayNextSong(nextSong)
                }
            }
        }
    }

    fun repeatMusic(looping: Boolean) {
        isLooping = looping
        Log.e("HoangDH", "$isLooping")
    }

    private fun autoPlayNextSong(songItem: PlaylistSongItem) {
        musicPlayer = MediaPlayer.create(this, songItem.resource?.toUri())
        startMusic()
    }

    fun pauseMusic() {
        Log.e("HoangDH", "pauseMusic")
        musicPlayer.pause()
        _songIsPlaying.postValue(false)
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
        val pendingIntent = NavDeepLinkBuilder(this)
            .setGraph(R.navigation.main_navigation)
            .setDestination(R.id.homeFragment)
            .setArguments(
                Bundle().apply {
                    putBoolean(NEED_OPEN_DIALOG, true)
                }
            )
            .createPendingIntent()


        val picture =
            MediaStore.Images.Media.getBitmap(this.contentResolver, songItem.songImage?.toUri())


        val notification =
            NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.spotify)
                .setLargeIcon(picture)
                .setContentTitle(songItem.songTitle)
                .setContentText(songItem.artists)

                .addAction(R.drawable.ic_pre, "Previous", null)
                .addAction(R.drawable.ic_play, "Play", null)
                .addAction(R.drawable.ic_next, "Next", null)
                .setStyle(
                    androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(1)
//                        .setMediaSession(mediaSession.sessionToken)
                )


                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setOnlyAlertOnce(true)
                .build()
        startForeground(1, notification)
    }

    private fun handleMusicAction(action: String) {
        when (action) {
            MusicAction.ACTION_PLAY -> {
                startMusic()
            }

            MusicAction.ACTION_PAUSE -> {
                pauseMusic()
            }

            MusicAction.ACTION_NEXT -> {
                if (songPosition < songList.size - 1) {
                    songPosition++
                    val nextSong = songList[songPosition]
                    _songLiveData.postValue(nextSong)
                    sendNotification(nextSong)
                    autoPlayNextSong(nextSong)
                }
            }

            MusicAction.ACTION_PREVIOUS -> {
                if (songPosition > 0) {
                    songPosition--
                    val nextSong = songList[songPosition]
                    _songLiveData.postValue(nextSong)
                    sendNotification(nextSong)
                    autoPlayNextSong(nextSong)
                }
            }
        }
    }

    private fun getPendingIntent(context: Context, action: String): PendingIntent {
        val intent = Intent(this, MyReceiver::class.java)
        Log.e("HoangDH", "getPendingIntent")
        intent.action = action
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }

}


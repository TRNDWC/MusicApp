package com.example.baseproject.service

import android.app.Notification
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
import com.example.baseproject.utils.Random
import com.example.core.base.BaseService


class MusicService : BaseService() {

    private lateinit var musicPlayer: MediaPlayer
    private var binder = MyBinder()
    private lateinit var mediaSession: MediaSessionCompat
    private lateinit var songItem: PlaylistSongItem
    private var isLoopingPlaylist: Boolean = false
    private var isShuffle: Boolean = false
    private val _songLiveData = MutableLiveData<PlaylistSongItem>()
    val songLiveData: LiveData<PlaylistSongItem> = _songLiveData
    private val _songIsPlaying: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val songIsPlaying: LiveData<Boolean> = _songIsPlaying
    lateinit var songList: List<PlaylistSongItem>
    lateinit var shuffleSongList: List<PlaylistSongItem>
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
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.getBundleExtra("song_bundle") != null) {
            val bundle = intent.getBundleExtra("song_bundle")
            songItem = bundle!!.getParcelable("song_item")!!
            songList = bundle.getParcelableArrayList("song_list")!!
            shuffleSongList = bundle.getParcelableArrayList("shuffle_song_list")!!
            songPosition = bundle.getInt("song_position")
            _songLiveData.value = songItem
            _songLiveData.postValue(songItem)
            musicPlayer = MediaPlayer.create(this, songItem.resource?.toUri())
            sendNotification(songItem)
            startMusic()

        }

        if (intent?.action != null) {
            handleMusicAction(intent.action!!)
        }

        return START_NOT_STICKY
    }


    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        musicPlayer.stop()
    }

    fun startMusic() {
        musicPlayer.start()
        sendNotification(_songLiveData.value!!)
        _songIsPlaying.postValue(true)
        if (isLoopingPlaylist) {
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
            else{
                musicPlayer.setOnCompletionListener {
                    stopSelf()
                    _songIsPlaying.postValue(false)
                }

            }
        }
    }

    fun reset() {
        musicPlayer.stop()
    }

    fun repeatPlaylist(loopingPlaylist: Boolean) {
        isLoopingPlaylist = loopingPlaylist
    }

    fun repeatSong(loopingSong: Boolean) {
        musicPlayer.isLooping = loopingSong
    }

    fun shuffleSong(shuffle: Boolean) {
        isShuffle = shuffle
    }

    private fun autoPlayNextSong(songItem: PlaylistSongItem) {
        reset()
        musicPlayer = MediaPlayer.create(this, songItem.resource?.toUri())
        startMusic()
    }

    fun pauseMusic() {
        musicPlayer.pause()
        sendNotification(_songLiveData.value!!)
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
        val pendingIntent = NavDeepLinkBuilder(this).setGraph(R.navigation.main_navigation)
            .setDestination(R.id.homeFragment).setArguments(Bundle().apply {
                putBoolean(NEED_OPEN_DIALOG, true)
            }).createPendingIntent()

        val picture =
            MediaStore.Images.Media.getBitmap(this.contentResolver, songItem.songImage?.toUri())

        val notification = NotificationCompat.Builder(this, CHANNEL_ID).apply {
            setSmallIcon(R.drawable.spotify)
            setLargeIcon(picture)
            setContentTitle(songItem.songTitle)
            setContentText(songItem.artists)
            addAction(
                R.drawable.ic_pre,
                "Previous",
                getPendingIntent(this@MusicService, MusicAction.ACTION_PREVIOUS)
            )
            if (musicPlayer.isPlaying) {
                addAction(
                    R.drawable.ic_pause,
                    "Pause",
                    getPendingIntent(this@MusicService, MusicAction.ACTION_PAUSE)
                )
            } else {
                addAction(
                    R.drawable.ic_play,
                    "Play",
                    getPendingIntent(this@MusicService, MusicAction.ACTION_PLAY)
                )
            }

            addAction(
                R.drawable.ic_next,
                "Next",
                getPendingIntent(this@MusicService, MusicAction.ACTION_NEXT)
            )
            setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setShowActionsInCompactView(1)

            )
            priority = NotificationCompat.PRIORITY_LOW
            setContentIntent(pendingIntent)
        }.build()
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
                } else {
                    songPosition = 0
                }
                val nextSong = songList[songPosition]
                _songLiveData.value = nextSong
                _songLiveData.postValue(nextSong)
                sendNotification(_songLiveData.value!!)
                autoPlayNextSong(_songLiveData.value!!)
            }

            MusicAction.ACTION_PREVIOUS -> {
                if (songPosition > 0) {
                    songPosition--
                    val nextSong = songList[songPosition]
                    _songLiveData.value = nextSong
                    _songLiveData.postValue(nextSong)
                    sendNotification(_songLiveData.value!!)
                    autoPlayNextSong(_songLiveData.value!!)
                }
            }
        }
    }

    private fun getPendingIntent(context: Context, action: String): PendingIntent {
        val intent = Intent(this, MyReceiver::class.java)
        intent.action = action
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_MUTABLE)
    }

}


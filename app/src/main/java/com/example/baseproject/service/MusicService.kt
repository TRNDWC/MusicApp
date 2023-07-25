package com.example.baseproject.service

import android.app.PendingIntent
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import com.example.baseproject.BaseApplication.Companion.CHANNEL_ID
import com.example.baseproject.R
import com.example.baseproject.data.model.PlaylistSongItem
import com.example.baseproject.ui.play.PlayFragment
import com.example.core.base.BaseService


class MusicService : BaseService() {

    private lateinit var musicPlayer: MediaPlayer
    private var binder = MyBinder()

    inner class MyBinder : Binder() {
        fun getMyService(): MusicService = this@MusicService
    }

    override fun onCreate() {
        super.onCreate()
        Log.e("HoangDH", "onCreate")
    }

    override fun onBind(intent: Intent?): IBinder {
        Log.e("HoangDH", "onBind")
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e("HoangDH", "onStartCommand")
        val songItem: PlaylistSongItem = intent!!.getBundleExtra("song_bundle")!!
            .getParcelable("song_item")!!
        musicPlayer = MediaPlayer.create(this, songItem.resource?.toUri())

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
        val intent = Intent(this, PlayFragment::class.java)
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(
                applicationContext,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )

        val remoteView = RemoteViews(packageName, R.layout.custom_notification).apply {
            setTextViewText(R.id.song_title, songItem.songTitle)
            setTextViewText(R.id.song_artist, songItem.artists)
            setImageViewUri(R.id.song_image, songItem.songImage?.toUri())
        }

        val notification =
            NotificationCompat.Builder(this, CHANNEL_ID).setSmallIcon(R.drawable.ic_notification)
                .setContentIntent(pendingIntent)
                .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(remoteView).build()
        startForeground(1, notification)
    }

}


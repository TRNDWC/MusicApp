package com.example.baseproject.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.baseproject.R
import com.example.baseproject.service.Notification.Companion.CHANNEL_ID
import com.example.baseproject.ui.play.PlayFragment
import com.example.baseproject.ui.playlist.PlaylistSongItem
import com.example.core.base.BaseService
import timber.log.Timber


class MusicService : BaseService() {

    private lateinit var musicPlayer: MediaPlayer

    override fun onCreate() {
        super.onCreate()
        createNotification()
        musicPlayer = MediaPlayer.create(applicationContext, R.raw.querry_qnt)
        Timber.tag("HoangDH").d("MusicService onCreate")
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val songItem : PlaylistSongItem = intent!!.getBundleExtra("song_bundle")!!.getSerializable("song_item") as PlaylistSongItem
        startMusic()
        sendNotification(songItem)

        return START_NOT_STICKY
    }

    private fun startMusic() {

        musicPlayer.start()
    }

    @SuppressLint("RemoteViewLayout")
    private fun sendNotification(songItem : PlaylistSongItem) {
        val intent = Intent(this, PlayFragment::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(this@MusicService, 0, intent, PendingIntent.FLAG_IMMUTABLE)


        val bitmap = BitmapFactory.decodeResource(resources, songItem.songImage)

        val remoteView = RemoteViews(packageName, R.layout.custom_notification)
        remoteView.setTextViewText(R.id.song_title, songItem.songTitle)
        remoteView.setTextViewText(R.id.song_artist, songItem.artists)
        remoteView.setImageViewBitmap(R.id.song_image, bitmap)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setCustomContentView(remoteView).build()
        startForeground(1, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
    private fun createNotification() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channelName = getString(R.string.channel_name)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, channelName, importance)

            val notificationManager : NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}


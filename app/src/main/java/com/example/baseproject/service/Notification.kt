package com.example.baseproject.service

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.example.baseproject.R

class Notification : Application() {

    companion object {
        const val CHANNEL_ID = "music_channel_id"
    }

    override fun onCreate() {
        super.onCreate()
    }

}
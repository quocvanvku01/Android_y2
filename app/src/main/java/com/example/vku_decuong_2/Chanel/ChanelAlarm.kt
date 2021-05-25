package com.example.vku_decuong_2.Chanel

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.example.vku_decuong_2.R

class ChanelAlarm: Application() {

    companion object {
        val CHANNEL_ID: String = "channel1"
    }

    override fun onCreate() {
        super.onCreate()

        createNotificationChannels();

    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

//            var uriSound: Uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.swiftly610)
//            var attributes: AudioAttributes = AudioAttributes.Builder()
//                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
//                .build()


            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            mChannel.description = descriptionText
            //mChannel.setSound(uriSound, attributes)
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }

}
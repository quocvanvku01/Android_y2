package com.example.vku_decuong_2.Service

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.vku_decuong_2.Chanel.ChanelAlarm
import com.example.vku_decuong_2.R
import com.example.vku_decuong_2.data.MonHoc_Model
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MyService: Service() {

    var list_MH = ArrayList<MonHoc_Model>()

    private val handler = Handler()
    private val runnable: Runnable = object : Runnable {
        override fun run() {

            var current: Calendar = Calendar.getInstance()

            var formatHour: SimpleDateFormat = SimpleDateFormat("HH")
            val gio:Int = formatHour.format(current.time).toInt()
            var formatMinutes: SimpleDateFormat = SimpleDateFormat("mm")
            val phut: Int = formatMinutes.format(current.time).toInt()
            var formatSe: SimpleDateFormat = SimpleDateFormat("ss")
            val giay: Int = formatSe.format(current.time).toInt()

            val week: Int = current.get(Calendar.DAY_OF_WEEK)



            list_MH.forEach {

                if (gio == 21 && phut == 57 && giay == 0) {
                    if (it.thu.toInt()-1 == week) {
                        sendNotification(it)
                    }
                }

                val thoigian = it.thoigian
                val giobatdau: Int = thoigian.substring(0, 2).toInt()
                val phutbatdau: Int = thoigian.substring(3, 5).toInt()

                if (gio == giobatdau - 1 && phut == phutbatdau) {
                    if(it.thu.toInt() == week) {
                        sendNotification(it)
                    }
                }

            }

            handler.postDelayed(this, 700)

        }
    }

    private fun sendNotification(it: MonHoc_Model) {
        val random: Random = Random()
        var m: Int = random.nextInt(9999 - 1000) + 1000
        Log.d("Temp", "Successfull")
        var notification: Notification = NotificationCompat.Builder(baseContext, ChanelAlarm.CHANNEL_ID)
                .setContentTitle(it.tenmon)
                .setContentText("Phòng " + it.phonghoc + " | Thứ " + it.thu + " | Tiết " + it.tiet)
                .setSmallIcon(R.drawable.alarm_clock)
                .setStyle(NotificationCompat.BigTextStyle().bigText("Phòng " + it.phonghoc
                        + " | Thứ " + it.thu + " | Tiết " + it.tiet ))
                .build()

        val notificationManager: NotificationManager = baseContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(m, notification)

        Thread.sleep(10000)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show()

        Log.d("start sv", "service start")

        val b = intent?.extras
        list_MH = b?.getSerializable("key") as ArrayList<MonHoc_Model>

        getDataMh()

        runnable.run()
        return Service.START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show()
        Log.d("Serive", "Sevice is Destroy")
        super.onDestroy()
    }

    fun getDataMh(): ArrayList<MonHoc_Model> {
        var b : ArrayList<MonHoc_Model> = ArrayList<MonHoc_Model>()
        b = list_MH
        Log.d("ListMH", list_MH.get(0).tenmon)
        return b
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)

        startBgService()

    }

    @SuppressLint("ServiceCast")
    private fun startBgService() {

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val myIntent = Intent(applicationContext,
                AlarmReceiver::class.java)
        val bundle = Bundle()
        myIntent.action = "FOO_ACTION"
        bundle.putSerializable("alarm", getDataMh())
        myIntent.putExtra("bundle", bundle)

        val pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, 0, pendingIntent)
    }

}
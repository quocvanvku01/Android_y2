package com.example.vku_decuong_2.Service

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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

    private var tDiBo : String = ""
    private var tXeDap : String = ""
    private var tXeMay : String = ""

    private var gDiHoc = 0
    private var pDiHoc = 0

    private var distanofschol: String = ""

    private lateinit var sharedPreferences: SharedPreferences
    private var setCheckedNoti: Boolean = true

    private val handler = Handler()
    private val runnable: Runnable = object : Runnable {
        override fun run() {

            setCheckedNoti = sharedPreferences.getBoolean("switchValue", false)

            var current: Calendar = Calendar.getInstance()

            var formatHour: SimpleDateFormat = SimpleDateFormat("HH")
            val gio:Int = formatHour.format(current.time).toInt()
            var formatMinutes: SimpleDateFormat = SimpleDateFormat("mm")
            val phut: Int = formatMinutes.format(current.time).toInt()
            var formatSe: SimpleDateFormat = SimpleDateFormat("ss")
            val giay: Int = formatSe.format(current.time).toInt()

            val week: Int = current.get(Calendar.DAY_OF_WEEK)

            list_MH.forEach {

                if (gio == 22 && phut == 23 && giay == 0) {

                    var weekTb: Int = it.thu.toInt()-1

                    if (it.thu.toInt() == 1) {
                        weekTb = 7
                    }

                    if ( weekTb == week && setCheckedNoti == true) {
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

                var tgadd: Calendar = Calendar.getInstance()

                tgadd.add(Calendar.HOUR, gDiHoc)
                tgadd.add(Calendar.MINUTE, (pDiHoc+15))

                if (1 == tgadd.get(Calendar.HOUR_OF_DAY) && 10 == tgadd.get(Calendar.MINUTE) && 0 == giay) {
                    if(it.thu.toInt() == week && setCheckedNoti == true) {
                        sendNotification2(it)
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
                .setSmallIcon(R.drawable.logo_vku)
                .setStyle(NotificationCompat.BigTextStyle().bigText("Phòng " + it.phonghoc
                        + " | Thứ " + it.thu + " | Tiết " + it.tiet + "\n" +
                        "Kế hoạch giảng dạy của học phần ngày mai: " + "\n" +
                        it.noidung ))
                .build()

        val notificationManager: NotificationManager = baseContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(m, notification)

        Thread.sleep(10000)
    }

    private fun sendNotification2(it: MonHoc_Model) {

        var kc: Double = distanofschol.toDouble()
        var kc2 = Math.round(kc * 10) / 10

        val random: Random = Random()
        var m: Int = random.nextInt(9999 - 1000) + 1000
        Log.d("Temp", "Successfull")
        var notification: Notification = NotificationCompat.Builder(baseContext, ChanelAlarm.CHANNEL_ID)
                .setContentTitle(it.tenmon)
                .setContentText("Phòng " + it.phonghoc + " | Thứ " + it.thu + " | Tiết " + it.tiet)
                .setSmallIcon(R.drawable.logo_vku)
                .setStyle(NotificationCompat.BigTextStyle().bigText("Phòng " + it.phonghoc
                        + " | Thứ " + it.thu + " | Tiết " + it.tiet + "\n" +
                        "Quãng đường đi hoc là: " + kc2 + "\n" +
                        "Thòi gian ước tính nêu di chuyển bằng xe máy là: " + tXeMay  + "\n" +
                        "Thòi gian ước tính nêu di chuyển bằng xe đạp là:: " + tXeDap + "\n"  +
                        "Thòi gian ước tính nêu đi bộ là:: " + tDiBo ))
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

        distanofschol = b?.getSerializable("locationlatlong") as String

        Log.d("location123", distanofschol.toString())

        getTimeDistan(distanofschol.toString())

        Log.d("tDiBo", tDiBo.toString())
        Log.d("tXeDap", tXeDap.toString())
        Log.d("tXeMay", tXeMay.toString())

        sharedPreferences = getSharedPreferences("key", 0)

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
        bundle.putSerializable("locationlatlong", distanofschol)
        bundle.putSerializable("alarm", getDataMh())
        myIntent.putExtra("bundle", bundle)

        val pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, 0, pendingIntent)
    }

    private fun getTimeDistan(ds: String) {
        var distan: Double = ds.toDouble()

        var vDiBo = 5.5
        var vXeDap = 15
        var vXeMay = 45

        var DiBo = distan/vDiBo
        var XeDap = distan/vXeDap
        var XeMay = distan/vXeMay

        tDiBo = getT(DiBo)
        tXeDap = getT(XeDap)
        tXeMay = getTDiHoc(XeMay)

    }

    private fun getT(t: Double): String {
        var t1 = t*60

        var t2: Double = t1/60

        if(t2 >= 1) {
            val phanNg: Int = t2.toInt()
            var t3: Double = t1 % 60
            val phanDu: Int = t3.toInt()

            return phanNg.toString() + " Giờ " + phanDu.toString() + " Phút"

        } else {
            var t4 = t1 % 60
            var t5: Int = t4.toInt()
            return t5.toString() + " Phút"
        }

        return ""
    }

    private fun getTDiHoc(t: Double): String {
        var t1 = t*60

        var t2: Double = t1/60

        if(t2 >= 1) {
            val phanNg: Int = t2.toInt()
            var t3: Double = t1 % 60
            val phanDu: Int = t3.toInt()

            gDiHoc = phanNg
            pDiHoc = phanDu

            return phanNg.toString() + " Giờ " + phanDu.toString() + " Phút"

        } else {
            var t4 = t1 % 60
            var t5: Int = t4.toInt()
            return t5.toString() + " Phút"
        }

        return ""
    }

}
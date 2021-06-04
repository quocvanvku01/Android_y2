package com.example.vku_decuong_2.Service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.vku_decuong_2.api.ApiClient
import com.example.vku_decuong_2.data.MonHoc_Model
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AlarmReceiver: BroadcastReceiver() {

    val NOTIFICATION_ID: Int = 1
    private var getIdgv: Int = 0
    private var temp: Int = 0
    var list_MH = ArrayList<MonHoc_Model>()
    private var distanofschol: String = ""

    override fun onReceive(context: Context?, intent: Intent?) {

        if(intent?.action == "FOO_ACTION") {

            val bundle = intent.getBundleExtra("bundle")
            val list_MH = bundle?.getSerializable("alarm") as ArrayList<MonHoc_Model>
            val distan = bundle?.getSerializable("locationlatlong") as String

            var b2 = Bundle()
            b2.putSerializable("key", list_MH)
            b2.putSerializable("locationlatlong", distan)

            val background = Intent(context, MyService::class.java)
            background.putExtras(b2)
            context!!.startService(background)

        }
    }

    private fun retrofitResponse(): Bundle {
        var b2 = Bundle()
        b2.putSerializable("key", list_MH)
        return b2
    }


}
package com.example.vku_decuong_2.data

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vku_decuong_2.R
import com.example.vku_decuong_2.adapter.KeHoachGiangDay_Adapter
import com.example.vku_decuong_2.api.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KeHoachGiangDay : AppCompatActivity() {

    private lateinit var rcv_Khgd: RecyclerView
    private lateinit var khgd_Adapter: KeHoachGiangDay_Adapter
    lateinit var progerssProgressDialog: ProgressDialog
    var list_Khgd = ArrayList<KeHoachGiangDay_Model>()
    private lateinit var value_id: String

    private var token_api: String = ""

    private lateinit var tvKhgd: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ke_hoach_giang_day)
        supportActionBar?.hide()

        val fontRegular = ResourcesCompat.getFont(this, R.font.jbmono_regular)!!
        val fontBold = ResourcesCompat.getFont(this, R.font.jbmono_bold)!!
        tvKhgd = findViewById(R.id.tv_khgd)
        tvKhgd.typeface = fontBold


        val setIsLogin = getSharedPreferences("isLogin", 0)
        token_api = setIsLogin?.getString("token_api", "").toString()

        var intent = intent

        value_id = intent.getStringExtra("id_decuong").toString()
        Toast.makeText(this, value_id, Toast.LENGTH_SHORT).show()

        rcv_Khgd = findViewById(R.id.rcv_khgd)

        var linerLayoutKhgd = LinearLayoutManager(this)
        rcv_Khgd.layoutManager = linerLayoutKhgd

        khgd_Adapter = KeHoachGiangDay_Adapter(list_Khgd, this)
        rcv_Khgd.adapter = khgd_Adapter

        progerssProgressDialog = ProgressDialog(this)
        progerssProgressDialog.setTitle("Loading")
        progerssProgressDialog.setCancelable(false)
        progerssProgressDialog.show()

        getDataKhgd()

    }

    private fun getDataKhgd() {
        val call: Call<List<KeHoachGiangDay_Model>> = ApiClient.getClient.getDataKhgd(value_id.toInt(),"Bearer "+token_api)
        call.enqueue(object: Callback<List<KeHoachGiangDay_Model>> {
            override fun onResponse(call: Call<List<KeHoachGiangDay_Model>>?, response : Response<List<KeHoachGiangDay_Model>>? ) {
                progerssProgressDialog.dismiss()
                list_Khgd.addAll(response!!.body()!!)
                rcv_Khgd.adapter!!.notifyDataSetChanged()
            }
            override fun onFailure(call: Call<List<KeHoachGiangDay_Model>>?, t : Throwable ? ) {
                progerssProgressDialog.dismiss()
                if (call!!.isCanceled()) {
                    Log.d("Error", "Call was cancelled forcefully")
                } else {
                    Log.d("Error", t!!.localizedMessage)
                }
            }
        })
    }
}
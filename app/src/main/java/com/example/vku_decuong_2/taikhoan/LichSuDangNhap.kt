package com.example.vku_decuong_2.taikhoan

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vku_decuong_2.R
import com.example.vku_decuong_2.adapter.DanhSachMonHoc_Adapter
import com.example.vku_decuong_2.adapter.LichSuDangNhap_Adapter
import com.example.vku_decuong_2.api.ApiClient
import com.example.vku_decuong_2.data.DanhSachMonHoc_Model
import com.example.vku_decuong_2.data.LichSuDangNhap_Model
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LichSuDangNhap : AppCompatActivity() {

    private lateinit var rcvLsdn: RecyclerView
    private lateinit var lsdn_Adapter: LichSuDangNhap_Adapter
    lateinit var progerssProgressDialog: ProgressDialog
    var list_Lsdn = ArrayList<LichSuDangNhap_Model>()
    private var provider: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lich_su_dang_nhap)
        supportActionBar?.hide()

        rcvLsdn = findViewById(R.id.rcv_lsdn)

        val setIsLogin = getSharedPreferences("isLogin", 0)
        provider = setIsLogin.getString("isLogin", "").toString()

        var linerLayoutLsdn = LinearLayoutManager(this)
        rcvLsdn.layoutManager = linerLayoutLsdn

        lsdn_Adapter = LichSuDangNhap_Adapter(list_Lsdn, this)
        rcvLsdn.adapter = lsdn_Adapter

        progerssProgressDialog = ProgressDialog(this)
        progerssProgressDialog.setTitle("Loading")
        progerssProgressDialog.setCancelable(false)
        progerssProgressDialog.show()


        getDataLsdn(provider)

    }

    private fun getDataLsdn(provider: String) {
        val call: Call<List<LichSuDangNhap_Model>> = ApiClient.getClient.getlichsudangnhap(provider)
        call.enqueue(object: Callback<List<LichSuDangNhap_Model>> {
            override fun onResponse(call: Call<List<LichSuDangNhap_Model>>?, response : Response<List<LichSuDangNhap_Model>>? ) {
                progerssProgressDialog.dismiss()
                list_Lsdn.addAll(response!!.body()!!)
                rcvLsdn.adapter!!.notifyDataSetChanged()
            }
            override fun onFailure(call: Call<List<LichSuDangNhap_Model>>?, t : Throwable ? ) {
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
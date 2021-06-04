package com.example.vku_decuong_2.taikhoan

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
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

    private var token_api: String = ""

    private lateinit var tvLsdn: TextView
    private lateinit var tvDstb: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lich_su_dang_nhap)
        supportActionBar?.hide()

        val fontBold = ResourcesCompat.getFont(this, R.font.jbmono_bold)
        val fontRegular = ResourcesCompat.getFont(this, R.font.jbmono_regular)

        rcvLsdn = findViewById(R.id.rcv_lsdn)

        val setIsLogin = getSharedPreferences("isLogin", 0)
        provider = setIsLogin.getString("isLogin", "").toString()
        token_api = setIsLogin?.getString("token_api", "").toString()

        var linerLayoutLsdn = LinearLayoutManager(this)
        rcvLsdn.layoutManager = linerLayoutLsdn

        lsdn_Adapter = LichSuDangNhap_Adapter(list_Lsdn, this)
        rcvLsdn.adapter = lsdn_Adapter

        progerssProgressDialog = ProgressDialog(this)
        progerssProgressDialog.setTitle("Loading")
        progerssProgressDialog.setCancelable(false)
        progerssProgressDialog.show()

        tvLsdn = findViewById(R.id.tv_lsdn)
        tvDstb = findViewById(R.id.tv_dstb)

        tvLsdn.typeface = fontBold
        tvDstb.typeface = fontRegular


        getDataLsdn(provider)

    }

    private fun getDataLsdn(provider: String) {
        val call: Call<List<LichSuDangNhap_Model>> = ApiClient.getClient.getlichsudangnhap(provider,"Bearer "+token_api)
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
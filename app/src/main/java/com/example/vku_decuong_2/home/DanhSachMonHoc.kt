package com.example.vku_decuong_2.home

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
import com.example.vku_decuong_2.api.ApiClient
import com.example.vku_decuong_2.data.DanhSachMonHoc_Model
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DanhSachMonHoc : AppCompatActivity() {

    private lateinit var rcv_Dsmh: RecyclerView
    private lateinit var dsmh_Adapter: DanhSachMonHoc_Adapter
    lateinit var progerssProgressDialog: ProgressDialog
    private var list_Dsmh = ArrayList<DanhSachMonHoc_Model>()

    private var token_api: String = ""

    private lateinit var tvDsmh3: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_danh_sach_mon_hoc)
        supportActionBar?.hide()

        val fontRegular = ResourcesCompat.getFont(this, R.font.jbmono_regular)!!
        val fontBold = ResourcesCompat.getFont(this, R.font.jbmono_bold)!!
        tvDsmh3 = findViewById(R.id.tv_dsmh_3)
        tvDsmh3.typeface = fontBold

        rcv_Dsmh = findViewById(R.id.rcv_dsmh)

        var linerLayoutDsmh = LinearLayoutManager(this)
        rcv_Dsmh.layoutManager = linerLayoutDsmh

        dsmh_Adapter = DanhSachMonHoc_Adapter(list_Dsmh, this)
        rcv_Dsmh.adapter = dsmh_Adapter

        progerssProgressDialog = ProgressDialog(this)
        progerssProgressDialog.setTitle("Loading")
        progerssProgressDialog.setCancelable(false)
        progerssProgressDialog.show()

        val setIsLogin = getSharedPreferences("isLogin", 0)
        val getIdgv:Int = setIsLogin.getInt("idgv", 0)
        token_api = setIsLogin?.getString("token_api", "").toString()

        getDataDsmh(getIdgv)

    }

    private fun getDataDsmh(idgv: Int) {
        val call: Call<List<DanhSachMonHoc_Model>> = ApiClient.getClient.getDataDsmh(idgv,"Bearer "+token_api)
        call.enqueue(object: Callback<List<DanhSachMonHoc_Model>> {
            override fun onResponse(call: Call<List<DanhSachMonHoc_Model>>?, response : Response<List<DanhSachMonHoc_Model>>? ) {
                progerssProgressDialog.dismiss()
                list_Dsmh.addAll(response!!.body()!!)
                rcv_Dsmh.adapter!!.notifyDataSetChanged()

                Log.d("tk123", "abc " + list_Dsmh.get(0).token123)

            }
            override fun onFailure(call: Call<List<DanhSachMonHoc_Model>>?, t : Throwable ? ) {
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
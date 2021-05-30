package com.example.vku_decuong_2.home

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_danh_sach_mon_hoc)
        supportActionBar?.hide()

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

        getDataDsmh(getIdgv)

    }

    private fun getDataDsmh(idgv: Int) {
        val call: Call<List<DanhSachMonHoc_Model>> = ApiClient.getClient.getDataDsmh(idgv)
        call.enqueue(object: Callback<List<DanhSachMonHoc_Model>> {
            override fun onResponse(call: Call<List<DanhSachMonHoc_Model>>?, response : Response<List<DanhSachMonHoc_Model>>? ) {
                progerssProgressDialog.dismiss()
                list_Dsmh.addAll(response!!.body()!!)
                rcv_Dsmh.adapter!!.notifyDataSetChanged()
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
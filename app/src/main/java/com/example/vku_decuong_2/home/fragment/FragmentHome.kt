package com.example.vku_decuong_2.home.fragment

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vku_decuong_2.LoginActivity
import com.example.vku_decuong_2.R
import com.example.vku_decuong_2.Service.MyService
import com.example.vku_decuong_2.StartLoadingData
import com.example.vku_decuong_2.adapter.LichHomNay_Adapter
import com.example.vku_decuong_2.api.ApiClient
import com.example.vku_decuong_2.data.MonHocHomNay_Model
import com.example.vku_decuong_2.data.MonHoc_Model
import com.example.vku_decuong_2.home.DanhSachMonHoc
import com.example.vku_decuong_2.taikhoan.DarkModePrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FragmentHome : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var mView: View
    private lateinit var btnDanhSachMonHoc: LinearLayout
    private lateinit var week: TextView

    private lateinit var rcv_Lhn: RecyclerView
    private lateinit var lhn_Adapter: LichHomNay_Adapter

    var list_Lhn : ArrayList<MonHocHomNay_Model> = ArrayList<MonHocHomNay_Model>()

    private lateinit var tvNameHome: TextView
    private lateinit var tvCountMonHoc: TextView

    private lateinit var tvTitleLichHome: TextView

    private var chucvu: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_home, container, false)

        if(DarkModePrefManager(context).isNightMode()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        week = mView.findViewById(R.id.week)
        week.setText(getWeek().toString())

        val setIsLogin = activity?.getSharedPreferences("isLogin", 0)
        var name = setIsLogin?.getString("name", "").toString()
        chucvu = setIsLogin?.getString("chucvu", "").toString()

        tvNameHome = mView.findViewById(R.id.tv_name_home)
        tvNameHome.text = name.toUpperCase()

        tvTitleLichHome = mView.findViewById(R.id.tv_title_lich_home)

        if (chucvu.equals("gv")) {
            tvTitleLichHome.text = "Lịch dạy ngày hôm nay"
        }

        if (chucvu.equals("sv")) {
            tvTitleLichHome.text = "Lịch học ngày hôm nay"
        }

        btnDanhSachMonHoc = mView.findViewById(R.id.btn_danh_sach_mon_hoc)
        btnDanhSachMonHoc.setOnClickListener{
            var intentDsmh = Intent(context, DanhSachMonHoc::class.java)
            startActivity(intentDsmh)
        }

        rcv_Lhn = mView.findViewById(R.id.rcv_lhn)

        var linerLayoutKhgd = LinearLayoutManager(context)
        rcv_Lhn.layoutManager = linerLayoutKhgd

        list_Lhn = StartLoadingData.getDataLhnLoading()

        lhn_Adapter = LichHomNay_Adapter(list_Lhn, context!!)
        rcv_Lhn.adapter = lhn_Adapter
        rcv_Lhn.adapter!!.notifyDataSetChanged()

        tvCountMonHoc = mView.findViewById(R.id.tv_count_monhoc)
        val count_mon_hoc: Int = StartLoadingData.getCountMonHoc()
        tvCountMonHoc.setText(count_mon_hoc.toString() + " Môn học")


        return mView
    }

    private fun getWeek(): Int {
        val currentDay: Calendar = Calendar.getInstance()
        Log.d("Thu", currentDay.get(Calendar.DAY_OF_WEEK).toString())
        val startDay: Date = Date("07/27/2020")
        val week =  (currentDay.time.time - startDay.time) / (7 * 24 * 3600 * 1000);
        return week.toInt()

    }


    companion object {

        var a = ArrayList<MonHoc_Model>()

        fun getdt(): ArrayList<MonHoc_Model> {
            return StartLoadingData.getDataMhLoading()
        }

        fun newInstance(param1: String, param2: String) =
                FragmentHome().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
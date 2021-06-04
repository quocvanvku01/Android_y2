package com.example.vku_decuong_2.home.fragment

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Fragment_2 : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var mView: View

    private lateinit var tvDsmh2: TextView

    private lateinit var rcv_Dsmh: RecyclerView
    private lateinit var dsmh_Adapter: DanhSachMonHoc_Adapter
    lateinit var progerssProgressDialog: ProgressDialog
    private var list_Dsmh = ArrayList<DanhSachMonHoc_Model>()

    private var token_api: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mView = inflater.inflate(R.layout.fragment_2, container, false)

        rcv_Dsmh = mView.findViewById(R.id.rcv_dsmh_fmhome)

        var linerLayoutDsmh = LinearLayoutManager(context)
        rcv_Dsmh.layoutManager = linerLayoutDsmh

        dsmh_Adapter = DanhSachMonHoc_Adapter(list_Dsmh, context!!)
        rcv_Dsmh.adapter = dsmh_Adapter

        progerssProgressDialog = ProgressDialog(context)
        progerssProgressDialog.setTitle("Loading")
        progerssProgressDialog.setCancelable(false)
        progerssProgressDialog.show()

        val setIsLogin = activity?.getSharedPreferences("isLogin", 0)
        val getIdgv:Int? = setIsLogin?.getInt("idgv", 0)
        token_api = setIsLogin?.getString("token_api", "").toString()

        if (getIdgv != null) {
            getDataDsmh(getIdgv)
        }

        val fontRegular = ResourcesCompat.getFont(context!!, R.font.jbmono_regular)!!
        val fontBold = ResourcesCompat.getFont(context!!, R.font.jbmono_bold)!!
        tvDsmh2 = mView.findViewById(R.id.tv_dsmh_2)
        tvDsmh2.typeface = fontBold

        return mView
    }

    private fun getDataDsmh(idgv: Int) {
        val call: Call<List<DanhSachMonHoc_Model>> = ApiClient.getClient.getDataDsmh(idgv,"Bearer "+token_api)
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Fragment_2.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Fragment_2().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
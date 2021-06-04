package com.example.vku_decuong_2.home.fragment

import android.app.ProgressDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vku_decuong_2.R
import com.example.vku_decuong_2.`interface`.OnClickLike
import com.example.vku_decuong_2.adapter.DanhSachMonHoc_Adapter
import com.example.vku_decuong_2.adapter.NewsFeed_Adapter
import com.example.vku_decuong_2.api.ApiClient
import com.example.vku_decuong_2.data.DanhSachMonHoc_Model
import com.example.vku_decuong_2.data.NewsFeed_Model
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment_1.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment_1 : Fragment(), OnClickLike {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var mView: View

    private var pageCurrent: Int = 1

    private lateinit var rcv_Nf: RecyclerView
    private lateinit var nf_Adapter: NewsFeed_Adapter
    lateinit var progerssProgressDialog: ProgressDialog
    private var list_nf = ArrayList<NewsFeed_Model>()

    private lateinit var tvVkuFeed: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_1, container, false)

        val fontBold = ResourcesCompat.getFont(context!!, R.font.jbmono_bold)!!
        val fontRegular = ResourcesCompat.getFont(context!!, R.font.jbmono_regular)!!

        rcv_Nf = mView.findViewById(R.id.rcv_news_feed)

        var linerLayoutDsmh = LinearLayoutManager(context)
        rcv_Nf.layoutManager = linerLayoutDsmh

        nf_Adapter = NewsFeed_Adapter(list_nf, context!!, this)
        rcv_Nf.adapter = nf_Adapter

        progerssProgressDialog = ProgressDialog(context)
        progerssProgressDialog.setTitle("Loading")
        progerssProgressDialog.setCancelable(false)
        progerssProgressDialog.show()

        getDataNf(pageCurrent)

        rcv_Nf.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    pageCurrent++
                    getDataNf(pageCurrent)
                    Toast.makeText(context, pageCurrent.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        })

        tvVkuFeed = mView.findViewById(R.id.tv_vku_feed)
        tvVkuFeed.typeface = fontBold

        return mView
    }

    private fun getDataNf(page : Int) {
        val call: Call<List<NewsFeed_Model>> = ApiClient.getClient.getnewsfeed(page)
        call.enqueue(object: Callback<List<NewsFeed_Model>> {
            override fun onResponse(call: Call<List<NewsFeed_Model>>?, response : Response<List<NewsFeed_Model>>? ) {
                progerssProgressDialog.dismiss()
                list_nf.addAll(response!!.body()!!)
                rcv_Nf.adapter!!.notifyDataSetChanged()
            }
            override fun onFailure(call: Call<List<NewsFeed_Model>>?, t : Throwable ? ) {
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
         * @return A new instance of fragment Fragment_1.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Fragment_1().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun OnLikeClick(newfeedmodel: NewsFeed_Model, tv: TextView) {

        if(newfeedmodel.liked == false) {
            tv.setTextColor(Color.parseColor("#F44336"))
            tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.heart2, 0, 0, 0)
            newfeedmodel.liked = true
        } else if (newfeedmodel.liked == true) {
            tv.setTextColor(Color.parseColor("#000000"))
            tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.heart, 0, 0, 0)
            newfeedmodel.liked = false
        }


    }
}
package com.example.vku_decuong_2.home.fragment

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import com.example.vku_decuong_2.LoginActivity
import com.example.vku_decuong_2.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment_2.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentTaiKhoan : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var mView: View

    private lateinit var btnLogout: LinearLayout

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
        mView = inflater.inflate(R.layout.fragment_tai_khoan, container, false)

        btnLogout = mView.findViewById(R.id.btn_logout)

        btnLogout.setOnClickListener{
            var logout: SharedPreferences? = this.activity?.getSharedPreferences("isLogin", 0)
            var editorLogout: SharedPreferences.Editor? = logout?.edit()
            editorLogout?.clear()
            editorLogout?.commit()
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
            val intentLogout = Intent(context, LoginActivity::class.java)
            startActivity(intentLogout)
        }

        return mView

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
            FragmentTaiKhoan().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
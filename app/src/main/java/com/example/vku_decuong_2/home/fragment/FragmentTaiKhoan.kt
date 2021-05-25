package com.example.vku_decuong_2.home.fragment

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.vku_decuong_2.LoginActivity
import com.example.vku_decuong_2.R
import com.example.vku_decuong_2.taikhoan.SettingTK
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions


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
    private lateinit var tvPhoneNumber: TextView
    private lateinit var tvNameTk: TextView
    private lateinit var tvEmailTk: TextView
    private lateinit var imSetting: ImageView

    private lateinit var mGoogleSignInClient: GoogleSignInClient

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
        tvPhoneNumber = mView.findViewById(R.id.tv_phone_number)
        tvNameTk = mView.findViewById(R.id.tv_name_tk)
        tvEmailTk = mView.findViewById(R.id.tv_email_tk)
        imSetting = mView.findViewById(R.id.im_setting)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(context!!, gso)

        btnLogout.setOnClickListener{
            signOut()
            var logout: SharedPreferences? = this.activity?.getSharedPreferences("isLogin", 0)
            var editorLogout: SharedPreferences.Editor? = logout?.edit()
            editorLogout?.clear()
            editorLogout?.commit()
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
            val intentLogout = Intent(context, LoginActivity::class.java)
            startActivity(intentLogout)

        }

        val setIsLogin = activity?.getSharedPreferences("isLogin", 0)
        var name = setIsLogin?.getString("name", "").toString()
        var email = setIsLogin?.getString("email", "").toString()

        tvNameTk.text = "Hi, " + name
        tvEmailTk.text = email

        imSetting.setOnClickListener {
            val intent_setting = Intent(context, SettingTK::class.java)
            startActivity(intent_setting)
        }

        return mView

    }

    private fun signOut() {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener {
                Toast.makeText(context, "Sigout Success", Toast.LENGTH_SHORT).show()
            }
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
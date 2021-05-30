package com.example.vku_decuong_2

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vku_decuong_2.api.ApiClient
import com.example.vku_decuong_2.data.LichSuDangNhap_Model
import com.example.vku_decuong_2.data.LoginRes
import com.example.vku_decuong_2.data.MonHocHomNay_Model
import com.example.vku_decuong_2.data.User_Model
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class LoginActivity : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: ImageView

    private lateinit var btnSigninGoogle: LinearLayout
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    private lateinit var userModel : User_Model

    var RC_SIGN_IN: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        //edtEmail = findViewById(R.id.edt_email)
        //edtPassword = findViewById(R.id.edt_password)
        //btnLogin = findViewById(R.id.btn_login)

        //btnLogin.setOnClickListener {
            //var username: String = edtEmail.text.toString()
            //var password: String = edtPassword.text.toString()

            //if(validateLogin(username, password)) {
                //doLogin(username, password)
            //}
        //}

        btnSigninGoogle = findViewById(R.id.btn_signin_google)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.server_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        btnSigninGoogle.setOnClickListener {
            signIn()
        }

    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            Log.d("Success", "Login Success full")

            val token: String? = account?.idToken
            val provider: String = "google"
            val email_ldn: String? = account?.email

            val calendar: Calendar = Calendar.getInstance()
            var format: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy  HH:mm")
            val ngaygio = format.format(calendar.time)
            val tenthietbi:String = Build.MODEL

            lichsudangnhap(tenthietbi, "Viet Nam", ngaygio, email_ldn.toString())

            if (token != null) {
                doLogin(provider, token)
            }

        } catch (e: ApiException) {
            Log.d("ERROR", "signInResult:failed code=" + e.statusCode)
        }
    }


    private fun validateLogin(username: String, pass: String): Boolean {
        if(username == null || username.trim().length == 0) {
            Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show()
            return false
        }
        if(pass == null || pass.trim().length == 0) {
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun doLogin(provider: String, token: String) {

        val call: Call<LoginRes> = ApiClient.getClient.login(provider, token)

        call.enqueue(object : Callback<LoginRes> {
            override fun onFailure(call: Call<LoginRes>, t: Throwable) {
                if (call!!.isCanceled()) {
                    Log.d("Error", "Call was cancelled forcefully")
                } else {
                    Log.d("Error", t!!.localizedMessage)
                }
            }

            override fun onResponse(call: Call<LoginRes>, response: Response<LoginRes>) {
                if(response.isSuccessful) {
                    if(response?.body()?.success.equals("true")) {
                        Log.d("Response123", "Success "+response?.body()?.privider)
                        Toast.makeText(this@LoginActivity, "Login Success", Toast.LENGTH_SHORT).show();

                        userModel = response.body()!!.user

                        val privider: String? = response?.body()?.privider

                        val id_gv: Int? = userModel.id.toInt()

                        var isLogin: SharedPreferences = applicationContext.getSharedPreferences("isLogin", 0)
                        var editorLogin: SharedPreferences.Editor = isLogin.edit()
                        editorLogin.putString("isLogin", privider)
                        editorLogin.putString("name", userModel.hodem + " " + userModel.ten)
                        editorLogin.putString("email", response?.body()?.email)
                        editorLogin.putString("chucvu", response?.body()?.chucvu)

                        if (id_gv != null) {
                            editorLogin.putInt("idgv", id_gv)
                            Log.d("IDGV", id_gv.toString())
                        }
                        editorLogin.commit()

                        val intenLg = Intent(this@LoginActivity, StartLoadingData::class.java)
                        startActivity(intenLg)

                    } else {
                        Toast.makeText(this@LoginActivity, "The username or password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "Error! Please try again!", Toast.LENGTH_SHORT).show();
                }
            }
        })
    }

    private fun lichsudangnhap(tenthietbi: String, vitri: String, ngaygio: String, email: String) {
        val call: Call<LichSuDangNhap_Model> = ApiClient.getClient.lichsudangnhap(tenthietbi, vitri, ngaygio, email)

        call.enqueue(object : Callback<LichSuDangNhap_Model> {
            override fun onFailure(call: Call<LichSuDangNhap_Model>, t: Throwable) {
                if (call!!.isCanceled()) {
                    Log.d("Error", "Call was cancelled forcefully")
                } else {
                    Log.d("Error", t!!.localizedMessage)
                }
            }

            override fun onResponse(call: Call<LichSuDangNhap_Model>, response: Response<LichSuDangNhap_Model>) {
                if(response.isSuccessful) {
                    Log.d("LSDN", "Success")
                } else {
                    Toast.makeText(this@LoginActivity, "Error! Please try again!", Toast.LENGTH_SHORT).show();
                }
            }

        })
    }

}
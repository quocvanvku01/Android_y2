package com.example.vku_decuong_2

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vku_decuong_2.api.ApiClient
import com.example.vku_decuong_2.data.LoginRes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        btnLogin = findViewById(R.id.btn_login)

        btnLogin.setOnClickListener {
            var username: String = edtEmail.text.toString()
            var password: String = edtPassword.text.toString()

            if(validateLogin(username, password)) {
                doLogin(username, password)
            }
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

    private fun doLogin(username: String, password: String) {

        val call: Call<LoginRes> = ApiClient.getClient.login(username, password)

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
                        Log.d("Response123", "Success "+response?.body()?.token)
                        Toast.makeText(this@LoginActivity, "Login Success", Toast.LENGTH_SHORT).show();

                        val token: String? = response?.body()?.token
                        var isLogin: SharedPreferences = applicationContext.getSharedPreferences("isLogin", 0)
                        var editorLogin: SharedPreferences.Editor = isLogin.edit()
                        editorLogin.putString("isLogin", token)
                        editorLogin.putInt("idgv", 133)
                        editorLogin.commit()

                        val intenLg = Intent(this@LoginActivity, MainActivity::class.java)
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

}
package com.example.vku_decuong_2.taikhoan

import android.content.Intent
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.vku_decuong_2.R


class SettingTK : AppCompatActivity() {

    private lateinit var darkModeSwitch: Switch

    private lateinit var tvLichSuDangNhap: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_t_k)
        supportActionBar?.hide()

        if(DarkModePrefManager(this).isNightMode()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        tvLichSuDangNhap = findViewById(R.id.tv_lichsudangnhap)

        tvLichSuDangNhap.setOnClickListener {
            val inten_lsdn = Intent(this, LichSuDangNhap::class.java)
            startActivity(inten_lsdn)
        }

        darkModeSwitch = findViewById(R.id.darkModeSwitch)

        setDarkModeSwitch()

    }

    private fun setDarkModeSwitch() {

        darkModeSwitch.isChecked = DarkModePrefManager(this).isNightMode()

        darkModeSwitch.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                val darkModePrefManager = DarkModePrefManager(this@SettingTK)
                darkModePrefManager.setDarkMode(!darkModePrefManager.isNightMode())
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                recreate()
            }

        })


    }
}
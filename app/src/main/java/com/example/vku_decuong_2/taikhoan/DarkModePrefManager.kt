package com.example.vku_decuong_2.taikhoan

import android.content.Context
import android.content.SharedPreferences

class DarkModePrefManager(val context: Context?) {

    var pref: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null
    var _context: Context? = null

    var PRIVATE_MODE = 0

    private val PREF_NAME = "education-dark-mode"
    private val IS_NIGHT_MODE = "IsNightMode"

    init {
        this._context = context
        pref = _context?.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref!!.edit()
    }


    fun setDarkMode(isFirstTime: Boolean) {
        editor!!.putBoolean(IS_NIGHT_MODE, isFirstTime)
        editor!!.commit()
    }

    fun isNightMode(): Boolean {
        return pref!!.getBoolean(IS_NIGHT_MODE, true)
    }

}
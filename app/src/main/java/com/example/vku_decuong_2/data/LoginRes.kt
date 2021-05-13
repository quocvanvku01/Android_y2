package com.example.vku_decuong_2.data

import com.google.gson.annotations.SerializedName

class LoginRes(

        @SerializedName("success")
        val success: String,
        @SerializedName("token")
        val token: String
)
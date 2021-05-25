package com.example.vku_decuong_2.data

import com.google.gson.annotations.SerializedName

class LoginRes(

        @SerializedName("success")
        val success: String,
        @SerializedName("privider")
        val privider: String,
        @SerializedName("email")
        val email: String,
        @SerializedName("chucvu")
        val chucvu: String,
        @SerializedName("user")
        val user: User_Model
)
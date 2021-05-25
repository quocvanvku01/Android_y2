package com.example.vku_decuong_2.data

import com.google.gson.annotations.SerializedName

class User_Model(
        @SerializedName("hodem")
        val hodem: String,
        @SerializedName("ten")
        val ten: String,
        @SerializedName("phone")
        val phone: String,
        @SerializedName("id")
        val id: String,
        @SerializedName("image")
        val image: String
)
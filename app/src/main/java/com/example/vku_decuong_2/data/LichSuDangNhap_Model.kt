package com.example.vku_decuong_2.data

import com.google.gson.annotations.SerializedName

class LichSuDangNhap_Model (
        @SerializedName("tenthietbi")
        val tenthietbi: String,
        @SerializedName("vitri")
        val vitri: String,
        @SerializedName("ngaygio")
        val ngaygio: String
)
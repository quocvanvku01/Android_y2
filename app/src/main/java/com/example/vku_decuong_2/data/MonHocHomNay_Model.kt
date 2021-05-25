package com.example.vku_decuong_2.data

import com.google.gson.annotations.SerializedName

class MonHocHomNay_Model(
        @SerializedName("ten_lhp")
        var tenmon: String,
        @SerializedName("phong")
        var phonghoc: String,
        @SerializedName("tiet")
        var tiet: String,
        @SerializedName("thoigian")
        var thoigian: String
)
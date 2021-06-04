package com.example.vku_decuong_2.data

import com.google.gson.annotations.SerializedName

class NewsFeed_Model(
        @SerializedName("nguoidang")
        var nguoidang: String,
        @SerializedName("tieude")
        var tieude: String,
        @SerializedName("ngay")
        var ngaygio: String,
        @SerializedName("noidung")
        var noidung: String,
        var liked: Boolean
)
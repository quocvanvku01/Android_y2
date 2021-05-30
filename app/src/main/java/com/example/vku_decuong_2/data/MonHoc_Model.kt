package com.example.vku_decuong_2.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MonHoc_Model(
        @SerializedName("ten_lhp")
        var tenmon: String,
        @SerializedName("phong")
        var phonghoc: String,
        @SerializedName("tiet")
        var tiet: String,
        @SerializedName("thu")
        var thu: String,
        @SerializedName("noidung")
        var noidung: String,
        @SerializedName("thoigian")
        var thoigian: String
) : Serializable
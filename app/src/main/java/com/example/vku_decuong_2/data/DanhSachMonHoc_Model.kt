package com.example.vku_decuong_2.data

import com.google.gson.annotations.SerializedName

class DanhSachMonHoc_Model(

        @SerializedName("tenhocphan")
        var tenmon: String,
        @SerializedName("id_decuong")
        var id_decuong: String

)
package com.example.vku_decuong_2.api

import com.example.vku_decuong_2.data.DanhSachMonHoc_Model
import com.example.vku_decuong_2.data.KeHoachGiangDay_Model
import com.example.vku_decuong_2.data.LoginRes
import retrofit2.Call
import retrofit2.http.*


interface ApiService {

    @FormUrlEncoded
    @POST("api/auth/login")
    fun login(
            @Field("email") email: String?,
            @Field("password") password: String?
    ): Call<LoginRes>

    @GET("api/decuong/get-list-mon-hoc/{id_gv}")
    fun getDataDsmh(@Path("id_gv") id_gv: Int): Call<List<DanhSachMonHoc_Model>>

    @GET("api/decuong/get-ke-hoach-giang-day/{id_decuong}")
    fun getDataKhgd(@Path("id_decuong") id_decuong: Int): Call<List<KeHoachGiangDay_Model>>

}
package com.example.vku_decuong_2.api

import com.example.vku_decuong_2.data.*
import retrofit2.Call
import retrofit2.http.*


interface ApiService {

    @FormUrlEncoded
    @POST("api/decuong/login-goole")
    fun login(
            @Field("provider") provider: String?,
            @Field("token") token: String?
    ): Call<LoginRes>

    @GET("api/decuong/get-list-mon-hoc/{id_gv}")
    fun getDataDsmh(@Path("id_gv") id_gv: Int): Call<List<DanhSachMonHoc_Model>>

    @GET("api/decuong/get-ke-hoach-giang-day/{id_decuong}")
    fun getDataKhgd(@Path("id_decuong") id_decuong: Int): Call<List<KeHoachGiangDay_Model>>

    @GET("api/decuong/get-lich-ngay-hom-nay/{id_gv}")
    fun getDataLhn(@Path("id_gv") id_gv: Int): Call<List<MonHocHomNay_Model>>

    @GET("api/decuong/get-lich/{id_gv}")
    fun getDataMonHoc(@Path("id_gv") id_gv: Int): Call<List<MonHoc_Model>>

    @FormUrlEncoded
    @POST("api/decuong/insert-lsdn")
    fun lichsudangnhap(
            @Field("tenthietbi") tenthietbi: String?,
            @Field("vitri") vitri: String?,
            @Field("ngaygio") ngaygio: String?,
            @Field("email") email: String?
    ): Call<LichSuDangNhap_Model>

}
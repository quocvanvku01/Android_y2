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
    fun getDataDsmh(@Path("id_gv") id_gv: Int, @Header("Authorization") authHeader: String): Call<List<DanhSachMonHoc_Model>>

    @GET("api/decuong/get-ke-hoach-giang-day/{id_decuong}")
    fun getDataKhgd(@Path("id_decuong") id_decuong: Int, @Header("Authorization") authHeader: String): Call<List<KeHoachGiangDay_Model>>

    @GET("api/decuong/get-lich-ngay-hom-nay/{id_gv}")
    fun getDataLhn(@Path("id_gv") id_gv: Int, @Header("Authorization") authHeader: String): Call<List<MonHocHomNay_Model>>

    @GET("api/decuong/get-lich/{id_gv}")
    fun getDataMonHoc(@Path("id_gv") id_gv: Int, @Header("Authorization") authHeader: String): Call<List<MonHoc_Model>>

    @FormUrlEncoded
    @POST("api/decuong/insert-lsdn")
    fun lichsudangnhap(
            @Field("tenthietbi") tenthietbi: String?,
            @Field("vitri") vitri: String?,
            @Field("ngaygio") ngaygio: String?,
            @Field("email") email: String?,
            @Header("Authorization") authHeader: String
    ): Call<LichSuDangNhap_Model>

    @GET("api/decuong/get-lich-su-dang-nhap/{provider}")
    fun getlichsudangnhap(@Path("provider") provider: String, @Header("Authorization") authHeader: String): Call<List<LichSuDangNhap_Model>>

    @GET("api/decuong/get-list-news-feed/{pageCurrent}")
    fun getnewsfeed(@Path("pageCurrent") pageCurrent: Int): Call<List<NewsFeed_Model>>

}
package com.ncs.imsUser.HTTPManager

import com.ncs.imsUser.HTTPManager.DTOManager.FindDTO
import com.ncs.imsUser.HTTPManager.DTOManager.PubicDTO
import com.ncs.imsUser.HTTPManager.DTOManager.UserDTO
import retrofit2.Call
import retrofit2.http.*

interface RetrofitInterface{

    @POST("/user/create")
    fun setUserInfo(@Body data: HashMap<String, String>): Call<UserDTO>

    @PATCH("/user/update")
    fun updateUserInfo(@Body data: HashMap<String, String>): Call<UserDTO>

    @POST("/emergency")
    fun sendMyEmergency(@Body data: HashMap<String, String>): Call<PubicDTO>

    @GET("/user/detail")
    fun loadUserInfo(@Query("kakaoId") kakaoId: String): Call<UserDTO>

    @POST("/find/hospital")
    fun getHospitalLocation(@Body data: HashMap<String, String>): Call<FindDTO>

    @POST("/find/pharmacy")
    fun getPharmacyLocation(@Body data: HashMap<String, String>): Call<FindDTO>
}
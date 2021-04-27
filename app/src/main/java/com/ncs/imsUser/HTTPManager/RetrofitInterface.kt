package com.ncs.imsUser.HTTPManager

import com.ncs.imsUser.HTTPManager.DTOManager.UserDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitInterface{
    @POST("/user/create")
    fun setUserInfo(@Body data: HashMap<String, String>): Call<UserDTO>

    @POST("/user/update")
    fun updateUserInfo(@Body data: HashMap<String, String>): Call<UserDTO>

}
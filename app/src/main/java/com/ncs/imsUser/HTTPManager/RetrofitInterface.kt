package com.ncs.imsUser.HTTPManager

import com.ncs.imsUser.HTTPManager.DTOManager.UserDTO
import retrofit2.Call
import retrofit2.http.*

interface RetrofitInterface{

    @POST("/user/create")
    fun setUserInfo(@Body data: HashMap<String, String>): Call<UserDTO>

    @PATCH("/user/update")
    fun updateUserInfo(@Body data: HashMap<String, String>): Call<UserDTO>

}
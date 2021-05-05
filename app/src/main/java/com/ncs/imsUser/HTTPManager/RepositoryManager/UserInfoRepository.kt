package com.ncs.imsUser.HTTPManager.RepositoryManager

import android.app.Application
import android.app.ProgressDialog
import android.text.Editable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kakao.sdk.user.model.User
import com.ncs.imsUser.HTTPManager.DTOManager.UserDTO
import com.ncs.imsUser.HTTPManager.DTOManager.UserData
import com.ncs.imsUser.HTTPManager.RetrofitAPI
import com.ncs.imsUser.HTTPManager.RetrofitInterface
import com.ncs.imsUser.HTTPManager.Tools
import com.ncs.imsUser.SaveDataManager.UserInfoData
import com.ncs.imsUser.ui.notifications.MypageFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.text.SimpleDateFormat
import java.util.*

class UserInfoRepository(application: Application) : AndroidViewModel(application){
    private var retrofit : Retrofit = RetrofitAPI.getInstance(Tools().MAIN_URL)
    private var service = retrofit.create(RetrofitInterface::class.java)
    var userInfoData = UserInfoData(application)
    val message = MutableLiveData<String>()
    fun insertUserData(userForm : Array<Editable>):LiveData<Boolean>{
        var resState = MutableLiveData<Boolean>()
        var gender = if(userForm[4].toString() == "남성") "1" else "0"
        var year = SimpleDateFormat("yyyy").format(SimpleDateFormat("yyyy-MM-dd").parse(userForm[2].toString()))

        var info = hashMapOf<String, String>(
                "kakaoId" to userInfoData.getUserData().get("USER_ID").toString(),
                "name" to userForm[0].toString(),
                "phone" to userForm[1].toString(),
                "birth" to userForm[2].toString(),
                "age" to (Calendar.getInstance()[Calendar.YEAR] - year.toInt()+1).toString(),
                "address" to userForm[3].toString(),
                "gender" to gender,
                "bloodType" to userForm[5].toString(),
                "imgSrc" to userInfoData.getUserData().get("IMGURL").toString(),
                "email" to userInfoData.getUserData().get("EMAIL").toString()
        )
        service.setUserInfo(info).enqueue(object : Callback<UserDTO> {
            override fun onResponse(call: Call<UserDTO>, response: Response<UserDTO>) {
                if(response.code() == 200)
                    resState.value = true
                else {
                    message.value = response.body()!!.message
                    resState.value = false
                }
            }

            override fun onFailure(call: Call<UserDTO>, t: Throwable) {
                Log.d("error", t.message.toString())
                message.value = t.message.toString()
                resState.value = false
            }
        })
        return resState
    }

    fun updateUserData(userForm : Array<Editable>):LiveData<Boolean>{
        var resState = MutableLiveData<Boolean>()

        var year = SimpleDateFormat("yyyy").format(SimpleDateFormat("yyyy-mm-dd").parse(userForm[2].toString()))
        var info = hashMapOf<String, String>(
                "kakaoId" to userInfoData.getUserData().get("USER_ID").toString(),
                "name" to userForm[0].toString(),
                "phone" to userForm[1].toString(),
                "age" to (Calendar.getInstance()[Calendar.YEAR] - year.toInt()+1).toString(),
                "address" to userForm[3].toString(),
                "bloodType" to userForm[5].toString()
        )

        service.updateUserInfo(info).enqueue(object : Callback<UserDTO> {
            override fun onResponse(call: Call<UserDTO>, response: Response<UserDTO>) {
                Log.d("Update State : ", response.body()!!.message)
                if(response.code() == 200)
                    resState.value = true
                else{
                    message.value = response.body()!!.message
                    resState.value = false
                }
            }

            override fun onFailure(call: Call<UserDTO>, t: Throwable) {
                Log.d("error", t.message.toString())
                message.value = t.message.toString()
                resState.value = false
            }
        })
        return resState
    }

    fun loadUserData(id: String):LiveData<UserData>{
        var userData = MutableLiveData<UserData>()
       service.loadUserInfo(id).enqueue(object : Callback<UserDTO>{
           override fun onResponse(call: Call<UserDTO>, response: Response<UserDTO>) {
               if(response.code() == 200){
                   userData.value = response.body()!!.result
               }else{
                   //message.value = response.body()!!.message
                   Log.e("state", response.body()!!.message)
               }
           }
           override fun onFailure(call: Call<UserDTO>, t: Throwable) {
               Log.e("Error", t.message.toString())
           }

       })
        return userData
    }
}
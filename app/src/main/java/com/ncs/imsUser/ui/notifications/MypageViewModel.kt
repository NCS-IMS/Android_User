package com.ncs.imsUser.ui.notifications

import android.app.Application
import android.app.ProgressDialog
import android.drm.ProcessedData
import android.text.Editable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import com.ncs.imsUser.HTTPManager.DTOManager.UserDTO
import com.ncs.imsUser.HTTPManager.RetrofitInterface
import com.ncs.imsUser.HTTPManager.Tools
import com.ncs.imsUser.SaveDataManager.UserInfoData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class MypageViewModel(application: Application) : AndroidViewModel(application) {
    var mApplication = application
    var userInfoData = UserInfoData(mApplication)
    var gson = GsonBuilder()
            .setLenient()
            .create()

    var retrofit = Retrofit.Builder()
            .baseUrl(Tools().BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    var service: RetrofitInterface = retrofit.create(RetrofitInterface::class.java)

    fun InsertUserData(userForm : Array<Editable>, progressDialog : ProgressDialog){

        var gender = if(userForm[4].toString() == "남성") "1" else "0"
        var year = SimpleDateFormat("yyyy").format(SimpleDateFormat("yyyy.mm.dd").parse(userForm[2].toString()))

        var info = hashMapOf<String, String>(
                "kakaoId" to userInfoData.getUserData().get("USER_ID").toString(),
                "name" to userForm[0].toString(),
                "phone" to userForm[1].toString(),
                "age" to (Calendar.getInstance()[Calendar.YEAR] - year.toInt()+1).toString(),
                "address" to userForm[3].toString(),
                "gender" to gender,
                "bloodType" to userForm[5].toString(),
                "imgSrc" to userInfoData.getUserData().get("IMGURL").toString(),
                "email" to userInfoData.getUserData().get("EMAIL").toString()
        )
        service.setUserInfo(info).enqueue(object : Callback<UserDTO> {
            override fun onResponse(call: Call<UserDTO>, response: Response<UserDTO>) {
                Log.d("Insert State : ", response.body()!!.message)
                progressDialog.dismiss()
                MypageFragment.first = !MypageFragment.first
            }

            override fun onFailure(call: Call<UserDTO>, t: Throwable) {
                Log.d("error", t.message.toString())
                progressDialog.dismiss()
            }

        })
    }

    fun UpdateUserData(userForm : Array<Editable>, progressDialog : ProgressDialog){
        var year = SimpleDateFormat("yyyy").format(SimpleDateFormat("yyyy.mm.dd").parse(userForm[2].toString()))

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
                progressDialog.dismiss()
            }

            override fun onFailure(call: Call<UserDTO>, t: Throwable) {
                Log.d("error", t.message.toString())
                progressDialog.dismiss()
            }

        })
    }

}
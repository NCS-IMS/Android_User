package com.ncs.imsUser.ui.home

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import com.ncs.imsUser.Dialog.SymptomDialogManager.SymptomDialog
import com.ncs.imsUser.HTTPManager.DTOManager.PubicDTO
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

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    var mApplication = application
    var userInfoData = UserInfoData(mApplication)
    var gson = GsonBuilder()
        .setLenient()
        .create()

    var retrofit = Retrofit.Builder()
        .baseUrl(Tools().EMERGENCY_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    var service: RetrofitInterface = retrofit.create(RetrofitInterface::class.java)

    fun myEmergencyCall(state : String, isMe: Boolean, gps : HashMap<String, Double>){

        var info = hashMapOf<String, String>(
            "kakaoId" to userInfoData.getUserData().get("USER_ID").toString(),
            "state" to state,
            "is_self" to if(isMe) "1" else "0",
            "latitude" to gps["latitude"].toString(),
            "longitude" to gps["longitude"].toString(),
            "medicine" to userInfoData.getUserData().get("MEDICINE").toString(),
            "anamnesis" to userInfoData.getUserData().get("HISTORY").toString()
        )
        service.sendMyEmergency(info).enqueue(object : Callback<PubicDTO>{
            override fun onResponse(call: Call<PubicDTO>, response: Response<PubicDTO>) {
                Log.d("Insert State : ", response.body()!!.message)
            }

            override fun onFailure(call: Call<PubicDTO>, t: Throwable) {
                Log.d("error", t.message.toString())
            }

        })
    }

    fun otherEmergencyCall(state : String, isMe: Boolean, gps : HashMap<String, Double>){

        var info = hashMapOf<String, String>(
                "kakaoId" to userInfoData.getUserData().get("USER_ID").toString(),
                "state" to state,
                "is_self" to if(isMe) "1" else "0",
                "latitude" to gps["latitude"].toString(),
                "longitude" to gps["longitude"].toString()
        )
        service.sendMyEmergency(info).enqueue(object : Callback<PubicDTO>{
            override fun onResponse(call: Call<PubicDTO>, response: Response<PubicDTO>) {
                Log.d("Insert State : ", response.body()!!.message)
            }
            override fun onFailure(call: Call<PubicDTO>, t: Throwable) {
                Log.d("error", t.message.toString())
            }
        })
    }
}
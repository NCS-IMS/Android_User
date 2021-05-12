package com.ncs.imsUser.HTTPManager.RepositoryManager

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ncs.imsUser.HTTPManager.DTOManager.PubicDTO
import com.ncs.imsUser.HTTPManager.RetrofitInterface
import com.ncs.imsUser.HTTPManager.Tools
import com.ncs.imsUser.HTTPManager.RetrofitAPI
import com.ncs.imsUser.SaveDataManager.UserInfoData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class CallRepository(application: Application) {
    private var retrofit : Retrofit = RetrofitAPI.getInstance(Tools().EMERGENCY_URL)
    private val service = retrofit.create(RetrofitInterface::class.java)
    var userInfoData = UserInfoData(application)

    //응급 호출 서버 요청 구간
    fun myEmergencyCall(state : String, isMe: Boolean, gps : HashMap<String, Double>):LiveData<Boolean>{
        var resState = MutableLiveData<Boolean>()
        var info = hashMapOf<String, String>(
                "kakaoId" to userInfoData.getUserData().get("USER_ID").toString(),
                "state" to state,
                "is_self" to if(isMe) "1" else "0",
                "latitude" to gps["latitude"].toString(),
                "longitude" to gps["longitude"].toString(),
                "medicine" to userInfoData.getUserData().get("MEDICINE").toString(),
                "anamnesis" to userInfoData.getUserData().get("HISTORY").toString(),
                "user_addr" to userInfoData.getUserData().get("ADDR").toString()
        )
        service.sendMyEmergency(info).enqueue(object : Callback<PubicDTO> {
            override fun onResponse(call: Call<PubicDTO>, response: Response<PubicDTO>) {
                Log.d("Insert State : ", response.body()!!.message)
                resState.value = true
            }
            override fun onFailure(call: Call<PubicDTO>, t: Throwable) {
                Log.d("error", t.message.toString())
                resState.value = false
            }
        })
        return resState
    }

    //제 3자가 호출할 때의 서버 요청할 때 사용
    fun otherEmergencyCall(state : String, isMe: Boolean, gps : HashMap<String, Double>):LiveData<Boolean>{
        var resState = MutableLiveData<Boolean>()
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
                resState.value = true
            }
            override fun onFailure(call: Call<PubicDTO>, t: Throwable) {
                Log.d("error", t.message.toString())
                resState.value = false
            }
        })
        return resState
    }
}
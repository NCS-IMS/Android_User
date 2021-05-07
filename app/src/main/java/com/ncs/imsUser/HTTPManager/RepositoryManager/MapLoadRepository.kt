package com.ncs.imsUser.HTTPManager.RepositoryManager

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ncs.imsUser.HTTPManager.DTOManager.FindDTO
import com.ncs.imsUser.HTTPManager.DTOManager.FindData
import com.ncs.imsUser.HTTPManager.RetrofitAPI
import com.ncs.imsUser.HTTPManager.RetrofitInterface
import com.ncs.imsUser.HTTPManager.Tools
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MapLoadRepository {
    private var retrofit : Retrofit = RetrofitAPI.getInstance(Tools().MAIN_URL)
    private var service = retrofit.create(RetrofitInterface::class.java)
    val message = MutableLiveData<String>()

    fun searchLocation(type:String, option:String, latitude: Double, longitude : Double):LiveData<List<FindData>>{
        var info = hashMapOf(
                "latitude" to latitude.toString(),
                "longitude" to longitude.toString(),
        )
        if(type=="병원")
            info.put("option", option)

        var LocationData = MutableLiveData<List<FindData>>()
        service.getHospitalLocation(info).enqueue(object : Callback<FindDTO>{
            override fun onResponse(call: Call<FindDTO>, response: Response<FindDTO>) {
                if(response.code() == 200){
                    LocationData.value = response.body()!!.result
                }else{
                    message.value = response.body()!!.message
                }
            }
            override fun onFailure(call: Call<FindDTO>, t: Throwable) {
                Log.d("error", t.message.toString())
            }
        })
        return LocationData
    }
}
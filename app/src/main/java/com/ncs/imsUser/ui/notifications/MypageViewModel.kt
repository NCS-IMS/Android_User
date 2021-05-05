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
import com.ncs.imsUser.HTTPManager.DTOManager.UserData
import com.ncs.imsUser.HTTPManager.RepositoryManager.UserInfoRepository
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


    private val userInfoRepository = UserInfoRepository(application)
    var userMessage = userInfoRepository.message

    fun insertUserData(userForm : Array<Editable>):LiveData<Boolean>{
        return userInfoRepository.insertUserData(userForm)
    }

    fun updateUserData(userForm : Array<Editable>):LiveData<Boolean>{
        return userInfoRepository.updateUserData(userForm)
    }

    fun loadUserData(id: String):LiveData<UserData>{
        return userInfoRepository.loadUserData(id)
    }

    fun responseMessage(): LiveData<String>{
        return userMessage
    }



}
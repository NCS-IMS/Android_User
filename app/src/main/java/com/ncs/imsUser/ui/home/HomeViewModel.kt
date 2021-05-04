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
import com.ncs.imsUser.HTTPManager.RepositoryManager.CallRepository
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
    private val callRepository = CallRepository(application)

    fun myCallMessage(state : String, isMe: Boolean, gps : HashMap<String, Double>):LiveData<Boolean>{
        return callRepository.myEmergencyCall(state, isMe, gps)
    }

    fun otherCallMessage(state : String, isMe: Boolean, gps : HashMap<String, Double>):LiveData<Boolean>{
        return callRepository.otherEmergencyCall(state, isMe, gps)
    }
}
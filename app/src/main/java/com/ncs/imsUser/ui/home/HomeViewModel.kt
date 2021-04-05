package com.ncs.imsUser.ui.home

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ncs.imsUser.Dialog.SymptomDialogManager.SymptomDialog

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    var mApplication = application

    /*private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text*/

    fun onClickButton(){

    }
}
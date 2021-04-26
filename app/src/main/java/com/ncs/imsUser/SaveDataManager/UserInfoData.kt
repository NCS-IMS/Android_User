package com.ncs.imsUser.SaveDataManager

import android.content.Context
import com.pddstudio.preferences.encrypted.EncryptedPreferences

class UserInfoData(context: Context) {
    var userData : EncryptedPreferences = EncryptedPreferences.Builder(context)
        .withEncryptionPassword("user")
        .build()
    var editor = userData.edit()
    fun setUserID(userID : String):Boolean{
        editor.putString("USER_ID",userID)
        return editor.commit()
    }
    fun setName(name : String):Boolean{
        editor.putString("NAME",name)
        return editor.commit()
    }
    fun setBirth(birth : String):Boolean{
        editor.putString("BIRTH",birth)
        return editor.commit()
    }
    fun setAddr(addr : String):Boolean{
        editor.putString("ADDR",addr)
        return editor.commit()
    }
    fun setGender(gender : String):Boolean{
        editor.putString("GENDER",gender)
        return editor.commit()
    }
    fun setBlood(blood : String):Boolean{
        editor.putString("BLOOD",blood)
        return editor.commit()
    }
    fun setMedicine(medicine : String):Boolean{
        editor.putString("MEDICINE",medicine)
        return editor.commit()
    }
    fun setHistory(history : String):Boolean{
        editor.putString("HISTORY",history)
        return editor.commit()
    }
    fun setProfileImg(imgUrl: String):Boolean{
        editor.putString("IMGURL",imgUrl)
        return editor.commit()
    }
    fun getUserData():HashMap<String, String>{
        val userInfo = HashMap<String, String>()
        userInfo["USER_ID"] = userData.getString("USER_ID", "")
        userInfo["NAME"] = userData.getString("NAME", "")
        userInfo["BIRTH"] = userData.getString("BIRTH", "")
        userInfo["ADDR"] = userData.getString("ADDR", "")
        userInfo["GENDER"] = userData.getString("GENDER", "")
        userInfo["BLOOD"] = userData.getString("BLOOD", "")
        userInfo["MEDICINE"] = userData.getString("MEDICINE", "")
        userInfo["HISTORY"] = userData.getString("HISTORY", "")
        userInfo["IMGURL"] = userData.getString("IMGURL", "")
        return userInfo
    }

    fun checkAllDataSave():Boolean{
        var userInfo = getUserData()
        for(value in userInfo.values){
            if(value.isEmpty())
                return false
        }
        return true
    }
}
package com.ncs.imsUser.ui.notifications

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.ncs.imsUser.R
import com.ncs.imsUser.SaveDataManager.UserInfoData
import com.ncs.imsUser.databinding.FragmentMypageBinding

class MypageFragment : Fragment, View.OnClickListener {

    var editorMode = false
    private lateinit var mypageViewModel: MypageViewModel
    lateinit var mypageBinding: FragmentMypageBinding
    lateinit var userInfoData: UserInfoData

    constructor()
    constructor(editorMode: Boolean){
        this.editorMode = editorMode
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        mypageViewModel = ViewModelProvider(requireActivity()).get(MypageViewModel::class.java)
        mypageBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_mypage, container, false)

        mypageBinding.myPageViewModel = mypageViewModel
        mypageBinding.lifecycleOwner = this
        userInfoData = UserInfoData(requireContext())
        loadUserInfo()
        if(editorMode){
            changeMode()
        }
        mypageBinding.editBtn.setOnClickListener(this)
        mypageBinding.saveBtn.setOnClickListener(this)
        return mypageBinding.root
    }
    fun changeMode(){
        mypageBinding.nameTxt.isEnabled = editorMode
        mypageBinding.birthTxt.isEnabled = editorMode
        mypageBinding.addrTxt.isEnabled = editorMode
        mypageBinding.genderTxt.isEnabled = editorMode
        mypageBinding.bloodTypeTxt.isEnabled = editorMode
        mypageBinding.takingMedicineTxt.isEnabled = editorMode
        mypageBinding.historyTxt.isEnabled = editorMode
        mypageBinding.editBtn.visibility = if(editorMode) View.GONE else View.VISIBLE
        mypageBinding.saveBtn.visibility = if(editorMode) View.VISIBLE else View.GONE
    }

    fun loadUserInfo(){
        var user = userInfoData.getUserData()
        mypageBinding.nameTxt.setText(user.get("NAME"))
        mypageBinding.birthTxt.setText(user.get("BIRTH"))
        mypageBinding.addrTxt.setText(user.get("ADDR"))
        mypageBinding.genderTxt.setText(user.get("GENDER"))
        mypageBinding.bloodTypeTxt.setText(user.get("BLOOD"))
        mypageBinding.takingMedicineTxt.setText(user.get("MEDICINE"))
        mypageBinding.historyTxt.setText(user.get("HISTORY"))
        Glide.with(requireContext()).load(user.get("IMGURL")).into(mypageBinding.profileImg)
    }

    fun checkFullText():Boolean{

        var array = arrayOf(
            mypageBinding.nameTxt.text,
            mypageBinding.birthTxt.text,
            mypageBinding.addrTxt.text,
            mypageBinding.genderTxt.text,
            mypageBinding.bloodTypeTxt.text,
            mypageBinding.takingMedicineTxt.text,
            mypageBinding.historyTxt.text
        )

        for(value in array){
            if(value.isEmpty()) return false
        }

        return true
    }

    fun saveUserData(){
        userInfoData.setName(mypageBinding.nameTxt.text.toString())
        userInfoData.setBirth(mypageBinding.birthTxt.text.toString())
        userInfoData.setAddr(mypageBinding.addrTxt.text.toString())
        userInfoData.setGender(mypageBinding.genderTxt.text.toString())
        userInfoData.setBlood(mypageBinding.bloodTypeTxt.text.toString())
        userInfoData.setMedicine(mypageBinding.takingMedicineTxt.text.toString())
        userInfoData.setHistory(mypageBinding.historyTxt.getText().toString())
    }

    fun showDialog(title : String, message : String){
        var dialog = AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("확인", object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {}}).show()
    }

    override fun onClick(v: View?) {
        this.editorMode = !editorMode
        when(v?.id){
            R.id.editBtn ->{
                changeMode()
            }
            R.id.saveBtn ->{
                if(checkFullText()) {
                    saveUserData()
                    changeMode()
                }
                else
                    showDialog("빈칸을 채워주세요","당장용>.<")
            }
        }
    }

}
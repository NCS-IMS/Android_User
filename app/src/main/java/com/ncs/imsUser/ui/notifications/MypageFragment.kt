package com.ncs.imsUser.ui.notifications

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import com.ncs.imsUser.HTTPManager.DTOManager.UserDTO
import com.ncs.imsUser.HTTPManager.RetrofitInterface
import com.ncs.imsUser.HTTPManager.Tools
import com.ncs.imsUser.R
import com.ncs.imsUser.SaveDataManager.UserInfoData
import com.ncs.imsUser.databinding.FragmentMypageBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class MypageFragment : Fragment, View.OnClickListener {

    var editorMode = false
    var first = false
    private lateinit var mypageViewModel: MypageViewModel
    lateinit var mypageBinding: FragmentMypageBinding
    lateinit var userInfoData: UserInfoData
    lateinit var userForm : Array<Editable>
    var gson = GsonBuilder()
            .setLenient()
            .create()

    var retrofit = Retrofit.Builder()
            .baseUrl(Tools().BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    var service: RetrofitInterface = retrofit.create(RetrofitInterface::class.java)
    lateinit var progressDialog:ProgressDialog

    constructor()
    constructor(editorMode: Boolean, first: Boolean){
        this.editorMode = editorMode
        this.first = first
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mypageViewModel = ViewModelProvider(requireActivity()).get(MypageViewModel::class.java)
        mypageBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_mypage, container, false)
        mypageBinding.myPageViewModel = mypageViewModel
        mypageBinding.lifecycleOwner = this

        var bloodType = arrayOf("A", "B", "O", "AB")
        var adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, bloodType)
        mypageBinding.bloodTypeTxt.setAdapter(adapter)

        userInfoData = UserInfoData(requireContext())
        loadUserInfo()
        if(editorMode){
            changeMode()
        }
        mypageBinding.phoneTxt.addTextChangedListener(PhoneNumberFormattingTextWatcher())
        mypageBinding.editBtn.setOnClickListener(this)
        mypageBinding.saveBtn.setOnClickListener(this)
        mypageBinding.birthTxt.setOnClickListener(this)
        progressDialog = ProgressDialog(requireContext())
        return mypageBinding.root
    }
    fun changeMode(){
        mypageBinding.nameTxt.isEnabled = editorMode
        mypageBinding.phoneTxt.isEnabled = editorMode
        mypageBinding.birthTxt.isEnabled = editorMode
        mypageBinding.addrTxt.isEnabled = editorMode
        mypageBinding.genderTxt.isEnabled = editorMode
        mypageBinding.genderTxt.isFocusable = !editorMode
        mypageBinding.bloodTypeTxt.isEnabled = editorMode
        mypageBinding.takingMedicineTxt.isEnabled = editorMode
        mypageBinding.historyTxt.isEnabled = editorMode
        mypageBinding.editBtn.visibility = if(editorMode) View.GONE else View.VISIBLE
        mypageBinding.saveBtn.visibility = if(editorMode) View.VISIBLE else View.GONE
    }

    fun loadUserInfo(){
        var user = userInfoData.getUserData()
        var gender = if(user.get("GENDER")=="MALE" || user.get("GENDER")=="남성") "남성" else "여성"
        mypageBinding.nameTxt.setText(user.get("NAME"))
        mypageBinding.phoneTxt.setText(user.get("PHONE"))
        mypageBinding.birthTxt.setText(user.get("BIRTH"))
        mypageBinding.addrTxt.setText(user.get("ADDR"))
        mypageBinding.genderTxt.setText(gender)
        mypageBinding.bloodTypeTxt.setText(user.get("BLOOD"))
        mypageBinding.takingMedicineTxt.setText(user.get("MEDICINE"))
        mypageBinding.historyTxt.setText(user.get("HISTORY"))
        Glide.with(requireContext()).load(user.get("IMGURL")).into(mypageBinding.profileImg)
    }

    fun checkFullText():Boolean{
        userForm = arrayOf(
                mypageBinding.nameTxt.text,
                mypageBinding.phoneTxt.text,
                mypageBinding.birthTxt.text,
                mypageBinding.addrTxt.text,
                mypageBinding.genderTxt.text,
                mypageBinding.bloodTypeTxt.text,
                mypageBinding.takingMedicineTxt.text,
                mypageBinding.historyTxt.text
        )
        for(value in userForm){
            if(value.isEmpty()) return false
        }
        return true
    }

    fun saveUserData(){
        userInfoData.setName(mypageBinding.nameTxt.text.toString())
        userInfoData.setPhone(mypageBinding.phoneTxt.text.toString())
        userInfoData.setBirth(mypageBinding.birthTxt.text.toString())
        userInfoData.setAddr(mypageBinding.addrTxt.text.toString())
        userInfoData.setGender(mypageBinding.genderTxt.text.toString())
        userInfoData.setBlood(mypageBinding.bloodTypeTxt.text.toString())
        userInfoData.setMedicine(mypageBinding.takingMedicineTxt.text.toString())
        userInfoData.setHistory(mypageBinding.historyTxt.getText().toString())
    }

    fun showDialog(title: String, message: String){
        var dialog = AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("확인", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {}
            }).show()
    }

    fun InsertUserData(){
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
        service.setUserInfo(info).enqueue(object : Callback<UserDTO>{
            override fun onResponse(call: Call<UserDTO>, response: Response<UserDTO>) {
                Log.d("Insert State : ", response.body()!!.message)
                progressDialog.dismiss()
            }

            override fun onFailure(call: Call<UserDTO>, t: Throwable) {
                Log.d("error", t.message.toString())
                progressDialog.dismiss()
            }

        })
    }

    override fun onClick(v: View?) {
        this.editorMode = !editorMode
        when(v?.id){
            mypageBinding.editBtn.id -> {
                changeMode()
            }
            mypageBinding.saveBtn.id -> {
                if (checkFullText()) {
                    saveUserData()
                    changeMode()
                    progressDialog.setMessage("저장 중 입니다...")
                    progressDialog.setCancelable(false)
                    progressDialog .setProgressStyle(ProgressDialog.STYLE_SPINNER)
                    progressDialog.show()
                    InsertUserData()
                }else showDialog("빈칸을 채워주세요", "당장용>.<")
            }
            mypageBinding.birthTxt.id -> {
                DatePickerDialog(requireContext(), { view, year, month, dayOfMonth ->
                    mypageBinding.birthTxt.setText("${year}.${month+1}.${dayOfMonth}")
                }, Calendar.getInstance()[Calendar.YEAR], Calendar.getInstance()[Calendar.MONTH], Calendar.getInstance()[Calendar.DATE]).show()
            }
        }
    }
}
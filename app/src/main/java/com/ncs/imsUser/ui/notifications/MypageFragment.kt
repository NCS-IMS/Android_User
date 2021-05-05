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
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
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
    lateinit var userForm: Array<Editable>
    lateinit var progressDialog: ProgressDialog

    constructor()
    constructor(editorMode: Boolean, first: Boolean) {
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
        if (editorMode) {
            changeMode()
        }
        mypageBinding.phoneTxt.addTextChangedListener(PhoneNumberFormattingTextWatcher())
        mypageBinding.editBtn.setOnClickListener(this)
        mypageBinding.saveBtn.setOnClickListener(this)
        mypageBinding.birthTxt.setOnClickListener(this)
        progressDialog = ProgressDialog(requireContext())
        return mypageBinding.root
    }

    fun changeMode() {
        mypageBinding.nameTxt.isEnabled = editorMode
        mypageBinding.phoneTxt.isEnabled = editorMode
        mypageBinding.birthTxt.isEnabled = editorMode
        mypageBinding.addrTxt.isEnabled = editorMode
        mypageBinding.genderTxt.isEnabled = editorMode
        mypageBinding.genderTxt.isFocusable = !editorMode
        mypageBinding.bloodTypeTxt.isEnabled = editorMode
        mypageBinding.takingMedicineTxt.isEnabled = editorMode
        mypageBinding.historyTxt.isEnabled = editorMode
        mypageBinding.editBtn.visibility = if (editorMode) View.GONE else View.VISIBLE
        mypageBinding.saveBtn.visibility = if (editorMode) View.VISIBLE else View.GONE
    }

    fun loadUserInfo() {
        var user = userInfoData.getUserData()
        var gender = if (user.get("GENDER") == "MALE" || user.get("GENDER") == "남성") "남성" else "여성"
        mypageBinding.genderTxt.setText(gender)
        mypageBinding.takingMedicineTxt.setText(user.get("MEDICINE"))
        mypageBinding.historyTxt.setText(user.get("HISTORY"))
        Glide.with(requireContext()).load(user.get("IMGURL")).into(mypageBinding.profileImg)
        mypageViewModel.loadUserData(user.get("USER_ID").toString()).observe(viewLifecycleOwner, {
            mypageBinding.nameTxt.setText(it.name)
            mypageBinding.phoneTxt.setText(it.phone)
            mypageBinding.birthTxt.setText(it.birth)
            mypageBinding.addrTxt.setText(it.address)
            mypageBinding.bloodTypeTxt.setText(it.bloodType)
        })
    }

    fun checkFullText(): Boolean {
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
        for (value in userForm) {
            if (value.isEmpty()) return false
        }
        return true
    }

    fun saveUserData() {
        userInfoData.setMedicine(mypageBinding.takingMedicineTxt.text.toString())
        userInfoData.setHistory(mypageBinding.historyTxt.getText().toString())
    }

    fun showDialog(title: String, message: String) {
        var dialog = AlertDialog.Builder(requireContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("확인", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {}
                }).show()
    }


    override fun onClick(v: View?) {
        this.editorMode = !this.editorMode
        when (v?.id) {
            mypageBinding.editBtn.id -> {
                changeMode()
            }
            mypageBinding.saveBtn.id -> {
                progressDialog.setMessage("저장 중 입니다...")
                progressDialog.setCancelable(false)
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                progressDialog.show()
                if (checkFullText() && first) {
                    mypageViewModel.insertUserData(userForm).observe(viewLifecycleOwner, {
                        if (it == true) {
                            Log.e("dfsdfsdfdsfsdfs", editorMode.toString())
                            changeMode()
                            saveUserData()
                            first = !first
                        } else {
                            mypageViewModel.responseMessage().observe(viewLifecycleOwner, {
                                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                            })
                        }
                        progressDialog.dismiss()
                    })
                } else if (checkFullText() && !first) {
                    mypageViewModel.updateUserData(userForm).observe(viewLifecycleOwner, {
                        if (it == true) {
                            changeMode()
                            saveUserData()
                        } else {
                            mypageViewModel.responseMessage().observe(viewLifecycleOwner, {
                                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                            })
                        }
                        progressDialog.dismiss()
                    })
                } else {
                    progressDialog.dismiss()
                    showDialog("빈칸을 채워주세요", "당장용>.<")
                }
            }
            mypageBinding.birthTxt.id -> {
                DatePickerDialog(requireContext(), { view, year, month, dayOfMonth ->
                    var month = Integer.toString(month +1)
                    var day = Integer.toString(dayOfMonth)
                    if(Integer.parseInt(month)<10) month = "0${month}"
                    if(Integer.parseInt(day)<10) day = "0${day}"

                    mypageBinding.birthTxt.setText("${year}-${month}-${day}")
                }, Calendar.getInstance()[Calendar.YEAR], Calendar.getInstance()[Calendar.MONTH], Calendar.getInstance()[Calendar.DATE]).show()
            }
        }
    }
}
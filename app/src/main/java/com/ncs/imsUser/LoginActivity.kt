package com.ncs.imsUser

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.ncs.imsUser.SaveDataManager.UserInfoData
import com.ncs.imsUser.databinding.ActivityLoginBinding
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class LoginActivity : AppCompatActivity() {
    lateinit var userData: UserInfoData
    var callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        Log.e("error?","sf")
        if(error != null){
            when{
                error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                    Toast.makeText(this, "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
                }
                error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                    Toast.makeText(this, "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
                }
                error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                    Toast.makeText(this, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT).show()
                }
                error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                    Toast.makeText(this, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
                }
                error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                    Toast.makeText(this, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
                }
                error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                    Toast.makeText(this, "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT).show()
                }
                error.toString() == AuthErrorCause.ServerError.toString() -> {
                    Toast.makeText(this, "서버 내부 에러", Toast.LENGTH_SHORT).show()
                }
                error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                    Toast.makeText(this, "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
                }
                else -> { // Unknown
                    Toast.makeText(this, "기타 에러"+error.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
        if (token != null) {
            UserApiClient.instance.me { user, error ->
                Log.e("dsfsd","sdfds")
                if (error != null) {
                    Log.e("Request Fail", "사용자 정보 요청 실패", error)
                }
                else if (user != null) {

                    userData.setUserID(user.id.toString())
                    userData.setGender(user.kakaoAccount?.gender.toString())
                    userData.setEmail(user.kakaoAccount?.email.toString())
                    userData.setProfileImg(user.kakaoAccount?.profile!!.profileImageUrl)

                    Log.e("sdfds", userData.getUserData().get("USER_ID").toString())

                    Log.i("Request Success", "사용자 정보 요청 성공" +
                            "\n회원번호: ${user.id}" +
                            "\n성별: ${user.kakaoAccount?.gender}")

                    Toast.makeText(this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }
    }

    lateinit var loginBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        //Log.d("KeyHash", getHashKey())
        Log.d("KeyHash", Utility.getKeyHash(this))

        userData = UserInfoData(this)

        loginBinding.kakaoLogin.setOnClickListener {
            UserApiClient.instance.run {
                if(isKakaoTalkLoginAvailable(this@LoginActivity)){
                    loginWithKakaoTalk(this@LoginActivity, callback = callback)
                }else{
                    Log.e("hello", "world")
                    loginWithKakaoAccount(this@LoginActivity, callback = callback)
                }
            }
        }
    }

}
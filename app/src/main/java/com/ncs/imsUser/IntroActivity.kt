package com.ncs.imsUser

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.kakao.sdk.user.UserApiClient

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        getPermission()
    }
    fun getPermission(){
        var permission = object : PermissionListener {
            override fun onPermissionGranted() {
                UserApiClient.instance.accessTokenInfo{ tokenInfo, error ->
                    if(tokenInfo != null){
                        startActivity(Intent(this@IntroActivity, MainActivity::class.java))
                        finish()
                    }else{
                        startActivity(Intent(this@IntroActivity, LoginActivity::class.java))
                        finish()
                    }
                }
            }
            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                finishAffinity()
            }

        }
        TedPermission.with(this)
            .setPermissionListener(permission)
            .setRationaleTitle("권한 요청")
            .setRationaleMessage("앱을 사용하기위해서는 권한 허용이 필요합니다!")
            .setDeniedMessage("승인 거부 [설정] > [권한]에서 권한 승인 가능")
            .setPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            .check()

    }

}
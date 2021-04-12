package com.ncs.imsUser.KakaoLogin

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ApprovalType
import com.ncs.imsUser.R


class GlobalApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, resources.getString(R.string.kakao_app_key))

    }



}
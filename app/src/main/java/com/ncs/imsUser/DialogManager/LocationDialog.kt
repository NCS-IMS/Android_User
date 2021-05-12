package com.ncs.imsUser.DialogManager

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kakao.sdk.navi.NaviClient
import com.kakao.sdk.navi.model.CoordType
import com.kakao.sdk.navi.model.Location
import com.kakao.sdk.navi.model.NaviOption
import com.ncs.imsUser.HTTPManager.DTOManager.FindData
import com.ncs.imsUser.ItemAdapterManager.LocationListAdapter
import com.ncs.imsUser.R
import com.ncs.imsUser.databinding.FindLocationLayoutBinding


class LocationDialog(var type: String, var mcontext: Context, var findLoadList: ArrayList<FindData>):BottomSheetDialogFragment() {

    lateinit var locationBinding: FindLocationLayoutBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        locationBinding = DataBindingUtil.inflate(inflater, R.layout.find_location_layout, container, false)
        locationBinding.findDialogTitle.text = "${type} 목록"
        Log.e("sds", findLoadList.toString())
        var locationListAdapter = LocationListAdapter(mcontext, findLoadList){
            Log.e("dfsdfds", it.place_name)
            //카카오 네비로 연결 하는 기능 추가

            //카카오 네비가 설치 되어 있으면 바로 연결
            if (NaviClient.instance.isKakaoNaviInstalled(mcontext)) {
                Log.i("Navi Able", "카카오내비 앱으로 길안내 가능")
                startActivity(
                        NaviClient.instance.navigateIntent(
                                Location(it.place_name, it.x, it.y),
                                NaviOption(coordType = CoordType.WGS84)
                        )
                )
            } else { //카카오 네비가 설치 되어 있지 않으면 설치 페이지로 이동
                Log.i("Navi Disable", "카카오내비 미설치: 웹 길안내 사용 권장")
                val intent = Intent(Intent.ACTION_VIEW)
                intent.addCategory(Intent.CATEGORY_DEFAULT)
                intent.data = Uri.parse("market://details?id=com.locnall.KimGiSa")
                startActivity(intent);
            }
        }
        val linearManger = LinearLayoutManager(mcontext)
        locationBinding.apiLactionList.adapter = locationListAdapter
        locationBinding.apiLactionList.layoutManager = linearManger
        locationBinding.apiLactionList.setHasFixedSize(true)
        locationListAdapter.notifyDataSetChanged()

        return locationBinding.root
    }
}
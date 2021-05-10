package com.ncs.imsUser.DialogManager

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ncs.imsUser.HTTPManager.DTOManager.FindData
import com.ncs.imsUser.ItemAdapterManager.LocationListAdapter
import com.ncs.imsUser.R
import com.ncs.imsUser.databinding.FindLocationLayoutBinding
import com.ncs.imsUser.databinding.ItemLocationBinding

class LocationDialog(var type: String, var mcontext: Context, var findLoadList : ArrayList<FindData>):BottomSheetDialogFragment() {

    lateinit var locationBinding: FindLocationLayoutBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        locationBinding = DataBindingUtil.inflate(inflater, R.layout.find_location_layout, container, false)
        locationBinding.findDialogTitle.text = "${type} 목록"
        Log.e("sds", findLoadList.toString())
        var locationListAdapter = LocationListAdapter(mcontext, findLoadList){
            Log.e("dfsdfds", it.place_name)
        }
        val linearManger = LinearLayoutManager(mcontext)
        locationBinding.apiLactionList.adapter = locationListAdapter
        locationBinding.apiLactionList.layoutManager = linearManger
        locationBinding.apiLactionList.setHasFixedSize(true)
        locationListAdapter.notifyDataSetChanged()

        return locationBinding.root
    }
}
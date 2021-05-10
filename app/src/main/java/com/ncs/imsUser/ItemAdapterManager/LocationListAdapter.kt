package com.ncs.imsUser.ItemAdapterManager

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ncs.imsUser.HTTPManager.DTOManager.FindData
import com.ncs.imsUser.R
import com.ncs.imsUser.databinding.ItemLocationBinding

class LocationListAdapter(var context: Context, var findLoadList : List<FindData>, var itemClick: (FindData)->Unit):RecyclerView.Adapter<LocationListAdapter.LocationListViewHolder>(){
    inner class LocationListViewHolder(itemLocationBinding: ItemLocationBinding, itemClick: (FindData) -> Unit?) : RecyclerView.ViewHolder(itemLocationBinding.root){
        var title = itemLocationBinding.locationTitle
        var address = itemLocationBinding.addressTxt
        var phone = itemLocationBinding.storePhoneTxt
        var direction = itemLocationBinding.direction

        fun bind(findData: FindData, context: Context){
            title.text = findData.place_name
            address.text = findData.address_name
            phone.text = findData.phone
            direction.text = findData.distance

            itemView.setOnClickListener{itemClick(findData)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): LocationListAdapter.LocationListViewHolder {
        var itemLocationBinding = ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Log.e("dfs", findLoadList.toString())
        Log.e("1234", "sdfsdfs")
        return LocationListViewHolder(itemLocationBinding, itemClick)
    }

    override fun onBindViewHolder(holder: LocationListAdapter.LocationListViewHolder,position: Int) {
        holder.bind(findLoadList[position], context)
    }

    override fun getItemCount(): Int {
        return findLoadList.size
    }



}
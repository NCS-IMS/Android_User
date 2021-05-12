package com.ncs.imsUser.ItemAdapterManager

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ncs.imsUser.HTTPManager.DTOManager.FindData
import com.ncs.imsUser.databinding.ItemLocationBinding
import java.util.*

class LocationListAdapter(var context: Context, var findLoadList: List<FindData>, var itemClick: (FindData) -> Unit):RecyclerView.Adapter<LocationListAdapter.LocationListViewHolder>(){
    inner class LocationListViewHolder(itemLocationBinding: ItemLocationBinding, itemClick: (FindData) -> Unit?) : RecyclerView.ViewHolder(itemLocationBinding.root){
        var title = itemLocationBinding.locationTitle
        var address = itemLocationBinding.addressTxt
        var phone = itemLocationBinding.storePhoneTxt
        var direction = itemLocationBinding.direction

        fun bind(findData: FindData, context: Context){
            title.text = findData.place_name
            address.text = findData.address_name
            phone.text = findData.phone
            direction.text = unitConveter(findData.distance)
            itemView.setOnClickListener{itemClick(findData)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationListAdapter.LocationListViewHolder {
        var itemLocationBinding = ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationListViewHolder(itemLocationBinding, itemClick)
    }

    override fun onBindViewHolder(holder: LocationListAdapter.LocationListViewHolder, position: Int) {
        holder.bind(findLoadList[position], context)
    }

    override fun getItemCount(): Int {
        return findLoadList.size
    }

    //거리 단위 변환
    fun unitConveter(distance: String):String{
        if(distance.length < 4){
            return distance +"m"
        }else{
            val d: Double = java.lang.String.format(Locale.KOREAN, "%.2f", distance.toDouble()/1000).toDouble() //소수점 둘째 자리까지 변환
            return d.toString()+"Km"
        }
    }



}
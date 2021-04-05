package com.ncs.imsUser.Dialog.SymptomDialogManager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.ncs.imsUser.R
import com.ncs.imsUser.databinding.SympCardBinding
import com.ncs.imsUser.databinding.SymptomBinding

class SymptomAdapter(var context: Context, var sympList: ArrayList<SymptomData>, var itemClick :(SymptomData)->Unit): RecyclerView.Adapter<SymptomAdapter.Holder>()  {
    inner class Holder(itemView: View, itemClick: (SymptomData) -> Unit):RecyclerView.ViewHolder(itemView){
        var sympImg = itemView.findViewById<ImageView>(R.id.sympImg)
        var sympName = itemView.findViewById<TextView>(R.id.sympName)
        fun bind(symptomData: SymptomData, context: Context){
            sympImg.setImageResource(symptomData.sympImg)
            sympName.text = symptomData.sympName
            itemView.setOnClickListener{itemClick(symptomData)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.symp_card, parent, false)
        return Holder(view, itemClick)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(sympList[position], context)
    }

    override fun getItemCount(): Int {
        return sympList.size
    }

}
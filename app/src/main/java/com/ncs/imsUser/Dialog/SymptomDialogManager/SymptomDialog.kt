package com.ncs.imsUser.Dialog.SymptomDialogManager

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.ncs.imsUser.R
import com.ncs.imsUser.databinding.SympCardBinding
import com.ncs.imsUser.databinding.SymptomBinding

class SymptomDialog(context: Context, var symptomDialogListener: SymptomDialogListener, var use: Int): DialogFragment() {

    lateinit var binding: SymptomBinding
    lateinit var sympCardbinding: SympCardBinding
    lateinit var sympList: ArrayList<SymptomData>
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.symptom, container, false)
        sympCardbinding = DataBindingUtil.inflate(inflater, R.layout.symp_card, container, false)
        sympList = SymptomListMaker(use)
        var adapter = SymptomAdapter(requireContext(),sympList){symptomData ->
            symptomDialogListener.clickItem(symptomData.sympName)
            dismiss()
        }
        binding.symptomRecycler.adapter = adapter
        val lm = GridLayoutManager(requireContext(),2)
        binding.symptomRecycler.layoutManager = lm
        binding.symptomRecycler.setHasFixedSize(true)
        return binding.root
    }
    fun SymptomListMaker(use:Int) : ArrayList<SymptomData>{
        var makeList : ArrayList<SymptomData> = arrayListOf()
        when(use){
            0 ->{
                makeList = arrayListOf<SymptomData>(
                    SymptomData(R.drawable.bone, "골절"),
                    SymptomData(R.drawable.burn, "화상"),
                    SymptomData(R.drawable.cut, "절단"),
                    SymptomData(R.drawable.out_sick, "외상"),
                    SymptomData(R.drawable.stomach_ache, "복통"),
                    SymptomData(R.drawable.head_ache, "두통"),
                    SymptomData(R.drawable.pregnant, "산통"),
                    SymptomData(R.drawable.question, "알수없음")
                )
            }
            1->{
                makeList = arrayListOf<SymptomData>(
                        SymptomData(R.drawable.anatomy, "내과"),
                        SymptomData(R.drawable.tooth, "치과"),
                        SymptomData(R.drawable.skeleton, "정형외과"),
                        SymptomData(R.drawable.ear, "이비인후과"),
                        SymptomData(R.drawable.pregnant, "산부인과"),
                        SymptomData(R.drawable.skin, "피부과"),
                        SymptomData(R.drawable.brain, "신경외과"),
                        SymptomData(R.drawable.eye, "안과")
                )
            }
        }
        return makeList
    }
    interface SymptomDialogListener{
        fun clickItem(state:String)
    }
}

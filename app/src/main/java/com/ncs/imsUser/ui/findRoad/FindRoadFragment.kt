package com.ncs.imsUser.ui.findRoad

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ncs.imsUser.Dialog.SymptomDialogManager.SymptomDialog
import com.ncs.imsUser.MainActivity
import com.ncs.imsUser.R
import com.ncs.imsUser.databinding.FragmentFindroadBinding


class FindRoadFragment : Fragment(), View.OnClickListener{

    private lateinit var findRoadViewModel: FindRoadViewModel
    lateinit var findroadBinding: FragmentFindroadBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        findRoadViewModel = ViewModelProvider(this).get(FindRoadViewModel::class.java)
        findroadBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_findroad,
            container,
            false
        )

        findroadBinding.findViewModel = findRoadViewModel
        findroadBinding.lifecycleOwner = this

        findroadBinding.hospitalBtn.setOnClickListener(this)

        return findroadBinding.root
    }

    override fun onClick(v: View?) {
        var fragment : Fragment
        when(v?.id){
            findroadBinding.hospitalBtn.id -> {
                var sympDialog = SymptomDialog(requireContext(), object : SymptomDialog.SymptomDialogListener{
                    override fun clickItem(state: String) {
                        fragment = MapFragment(state)
                        (activity as MainActivity?)!!.replaceFragment(fragment)
                    }
                }, 1)
                sympDialog.show(childFragmentManager, sympDialog.tag)
            }
        }
    }
}
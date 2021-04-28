package com.ncs.imsUser.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ncs.imsUser.Dialog.SymptomDialogManager.SymptomDialog
import com.ncs.imsUser.GISManager.GetMylocation
import com.ncs.imsUser.R
import com.ncs.imsUser.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), View.OnClickListener, View.OnLongClickListener{

    private lateinit var homeViewModel: HomeViewModel
    lateinit var homeBinding: FragmentHomeBinding
    lateinit var getMylocation: GetMylocation
    var isMe = false
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        homeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        /*homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        homeBinding.homeViewModel = homeViewModel
        homeBinding.lifecycleOwner = this

        getMylocation = GetMylocation()
        homeBinding.callBtn.setOnClickListener(this)
        homeBinding.callBtn.setOnLongClickListener(this)
        return homeBinding.root
    }

    override fun onLongClick(v: View?): Boolean {
        when(v?.id){
            homeBinding.callBtn.id->{
                var gps = getMylocation.getLocation(requireContext())
                Log.e("longitude", gps.get("longitude").toString())
                Log.e("latitude", gps.get("latitude").toString())
                return true
            }
        }
        return false
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.call_btn->{
                isMe = true
                var sympDialog = SymptomDialog(requireContext(), object:SymptomDialog.SymptomDialogListener{
                    override fun clickItem(state: String) {
                        Log.e("hello", state)

                    }
                })
                sympDialog.show(childFragmentManager, sympDialog.tag)
            }
        }
    }
}
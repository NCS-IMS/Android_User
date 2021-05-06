package com.ncs.imsUser.ui.findRoad

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ncs.imsUser.R
import com.ncs.imsUser.databinding.FragmentMapBinding
import net.daum.mf.map.api.MapView

class MapFragment: Fragment() {
    private lateinit var mapViewModel: MapViewModel
    lateinit var mapBinding: FragmentMapBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        mapViewModel = ViewModelProvider(requireActivity()).get(MapViewModel::class.java)
        mapBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false)

        mapBinding.mapViewModel = mapViewModel
        mapBinding.lifecycleOwner = this


        var map_view = MapView(requireActivity())
        mapBinding.mapView.addView(map_view)

        return mapBinding.root
    }
}
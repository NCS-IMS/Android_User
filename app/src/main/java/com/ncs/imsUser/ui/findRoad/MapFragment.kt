package com.ncs.imsUser.ui.findRoad

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ncs.imsUser.GISManager.GetMylocation
import com.ncs.imsUser.MainActivity
import com.ncs.imsUser.R
import com.ncs.imsUser.databinding.FragmentMapBinding
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class MapFragment(var type: String, var option : String): Fragment(){
    private lateinit var mapViewModel: MapViewModel
    lateinit var mapBinding: FragmentMapBinding
    lateinit var gps : HashMap<String, Double>
    lateinit var marker : MapPOIItem
    lateinit var map_view: MapView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        mapViewModel = ViewModelProvider(requireActivity()).get(MapViewModel::class.java)
        mapBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false)

        mapBinding.mapViewModel = mapViewModel
        mapBinding.lifecycleOwner = this

        gps = GetMylocation().getLocation(requireContext())

        map_view = MapView(requireActivity())
        mapBinding.mapView.addView(map_view)
        mapBinding.findType.text = "$type 찾기"
        setCurrentLocation()
        mapViewModel.findLocation(type, option, gps.get("latitude")!!, gps.get("longitude")!!).observe(viewLifecycleOwner, {
            for(i in it){
                Log.e("host", i.address_name)
            }
        })

        return mapBinding.root
    }

    fun setCurrentLocation(){
        var mapPoint = MapPoint.mapPointWithGeoCoord(gps.get("latitude")!!, gps.get("longitude")!!)
        map_view.setMapCenterPointAndZoomLevel(mapPoint, 5, true)
        map_view.zoomIn(true)
        map_view.zoomOut(true)
        setCurretMarker(mapPoint)
    }

    fun setCurretMarker(mapPoint : MapPoint){
        marker = MapPOIItem()
        marker.itemName = "내위치"
        marker.mapPoint = mapPoint
        marker.tag = 0
        marker.markerType = MapPOIItem.MarkerType.BluePin
        map_view.addPOIItem(marker)
    }
}
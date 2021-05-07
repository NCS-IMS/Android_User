package com.ncs.imsUser.ui.findRoad

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ncs.imsUser.HTTPManager.DTOManager.FindData
import com.ncs.imsUser.HTTPManager.RepositoryManager.MapLoadRepository

class MapViewModel(application: Application) : AndroidViewModel(application)  {

    var mapRepository = MapLoadRepository()

    fun findLocation(type: String, option:String, latitude: Double, longitude : Double):LiveData<List<FindData>>{
        return mapRepository.searchLocation(type, option, latitude, longitude)
    }

}
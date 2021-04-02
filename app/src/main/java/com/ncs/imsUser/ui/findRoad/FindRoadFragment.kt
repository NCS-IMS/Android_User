package com.ncs.imsUser.ui.findRoad

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ncs.imsUser.R

class FindRoadFragment : Fragment() {

    private lateinit var findRoadViewModel: FindRoadViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        findRoadViewModel =
                ViewModelProvider(this).get(FindRoadViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_findroad, container, false)

        return root
    }
}
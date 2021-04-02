package com.ncs.imsUser.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ncs.imsUser.R

class MypageFragment : Fragment() {

    private lateinit var mypageViewModel: MypageViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        mypageViewModel =
                ViewModelProvider(this).get(MypageViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_mypage, container, false)

        return root
    }
}
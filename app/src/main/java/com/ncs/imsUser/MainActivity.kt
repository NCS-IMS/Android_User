package com.ncs.imsUser

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ncs.imsUser.SaveDataManager.UserInfoData
import com.ncs.imsUser.ui.notifications.MypageFragment

class MainActivity : AppCompatActivity() {

    lateinit var navView : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var userData = UserInfoData(this)
        navView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)

        if(!userData.checkAllDataSave()){
            Log.e("dfds", "ad2e21321313123213")
            showDialog("추가정보 입력", "추가정보를 입력해야합니다.\n입력페이지로 이동합니다.")
        }else{
            Log.e("dfds", "dsfsd")
        }
    }

    fun showDialog(title : String, message : String){
        var dialog = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("확인", object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, MypageFragment(true, true)).commit()
                    navView.menu.get(2).isChecked = true
                }
            }).show()
    }

}
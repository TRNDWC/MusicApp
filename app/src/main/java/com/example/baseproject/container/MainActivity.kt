package com.example.baseproject.container

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.example.baseproject.R
import com.example.baseproject.databinding.ActivityMainBinding
import com.example.baseproject.navigation.AppNavigation
import com.example.baseproject.utils.PermissionsUtil
import com.example.baseproject.utils.PermissionsUtil.requestPermissions
import com.example.core.base.BaseActivityNotRequireViewModel
import com.example.core.pref.RxPreferences
import com.example.core.utils.NetworkConnectionManager
import com.example.core.utils.setLanguage
import com.example.core.utils.toast
import com.example.setting.ui.home.DemoDialogListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : BaseActivityNotRequireViewModel<ActivityMainBinding>(), DemoDialogListener {

    @Inject
    lateinit var appNavigation: AppNavigation

    @Inject
    lateinit var networkConnectionManager: NetworkConnectionManager

    @Inject
    lateinit var rxPreferences: RxPreferences

    override val layoutId = R.layout.activity_main
    private val STORAGE_PERMISSION_ID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!checkStorePermission(STORAGE_PERMISSION_ID)) {
            showRequestPermission(STORAGE_PERMISSION_ID)
        }

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host) as NavHostFragment
        appNavigation.bind(navHostFragment.navController)

        lifecycleScope.launch {
            val language = rxPreferences.getLanguage().first()
            language?.let { setLanguage(it) }
        }

        networkConnectionManager.isNetworkConnectedFlow
            .onEach {
                if (it) {
                    Log.d("ahihi", "onCreate: Network connected")
                } else {
                    Log.d("ahihi", "onCreate: Network disconnected")
                }
            }
            .launchIn(lifecycleScope)
    }

    private fun checkStorePermission(permission: Int): Boolean {
        return if (permission == STORAGE_PERMISSION_ID) {
            PermissionsUtil.checkPermissions(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        } else {
            true
        }
    }

    private fun showRequestPermission(requestCode: Int) {
        val permissions: Array<String> = if (requestCode == STORAGE_PERMISSION_ID) {
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        } else {
            arrayOf(
                Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
        requestPermissions(this, requestCode, *permissions)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0) {
            var i = 0
            val len = permissions.size
            while (i < len) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    // TODO: 2/25/19 get song list here as user as accept the storage permisssion.
                    return
                }
                i++
            }
        }
    }

    override fun onStart() {
        super.onStart()
        networkConnectionManager.startListenNetworkState()
    }

    override fun onStop() {
        super.onStop()
        networkConnectionManager.stopListenNetworkState()
    }

    override fun onClickOk() {
        "Ok Activity".toast(this)
    }

    override fun onClickCancel() {
        "Cancel Activity".toast(this)
    }

}
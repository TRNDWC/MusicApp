package com.example.baseproject.container

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.example.baseproject.R
import com.example.baseproject.databinding.ActivityMainBinding
import com.example.baseproject.navigation.AppNavigation
import com.example.baseproject.utils.LanguageConfig.changeLanguage
import com.example.baseproject.utils.PermissionsUtil
import com.example.baseproject.utils.PermissionsUtil.requestPermissions
import com.example.baseproject.utils.SharedPrefs
import com.example.core.base.BaseActivity
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
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), DemoDialogListener {

    @Inject
    lateinit var appNavigation: AppNavigation

    @Inject
    lateinit var networkConnectionManager: NetworkConnectionManager

    @Inject
    lateinit var rxPreferences: RxPreferences

    var sharedPreferences: SharedPrefs? = null

    private val viewModel: MainViewModel by viewModels()
    override fun getVM() = viewModel
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (requestCode == STORAGE_PERMISSION_ID) {
                requestPermissions(
                    this,
                    requestCode,
                    Manifest.permission.READ_MEDIA_AUDIO,
                )
            }
        } else {
            if (requestCode == STORAGE_PERMISSION_ID) {
                requestPermissions(
                    this,
                    requestCode,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            }
        }
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
                    viewModel.getSong()
                    return
                }
                i++
            }
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        sharedPreferences = SharedPrefs(newBase!!)
        val languageCode: String = sharedPreferences!!.locale
        val context: Context = changeLanguage(newBase, languageCode)
        super.attachBaseContext(context)
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
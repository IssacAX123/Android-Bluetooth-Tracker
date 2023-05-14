package com.iax3m.bluetoothtracker.android

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.iax3m.bluetoothtracker.databinding.ActivityDeviceListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DeviceListActivity : AppCompatActivity() {
    lateinit var binding:ActivityDeviceListBinding
    lateinit var bannerTopAdView : AdView
    lateinit var bannerBottomAdView : AdView
    val viewModel:DeviceListViewModel by viewModels()

    private val bluetoothManager by lazy {
        applicationContext.getSystemService(BluetoothManager::class.java)
    }

    private val bluetoothAdapter by lazy {
        bluetoothManager?.adapter
    }

    private val isBluetoothEnabled: Boolean
        get() = bluetoothAdapter?.isEnabled == true
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeviceListBinding.inflate(layoutInflater)
        val view: View = binding.root
        bannerTopAdView = binding.deviceListAdBanner1
        bannerBottomAdView = binding.deviceListAdBanner2
        setContentView(view)

        val enableBluetoothLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){}
        val permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()){permissions ->
            val canEnableBluetooth = permissions[android.Manifest.permission.BLUETOOTH_CONNECT] == true
            if (canEnableBluetooth && !isBluetoothEnabled){
                enableBluetoothLauncher.launch(
                    Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                )
            }
        }

        permissionLauncher.launch(
            arrayOf(
                android.Manifest.permission.BLUETOOTH_SCAN,
                android.Manifest.permission.BLUETOOTH_CONNECT
            )
        )
        MobileAds.initialize(this) {}
        val adRequest1 = AdRequest.Builder().build()
        bannerTopAdView.loadAd(adRequest1)
        val adRequest2 = AdRequest.Builder().build()
        bannerBottomAdView.loadAd(adRequest2)

        viewModel.startSearch()
        lifecycleScope.launch{
            viewModel.state.collect{state ->
                val scrollViewConstraintLayout = binding.deviceListScrollViewLinearLayout
                state.devices.forEach{device ->
                    val button = Button(applicationContext)
                    button.text = device.name
                    button.textAlignment = View.TEXT_ALIGNMENT_CENTER
                    button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 36F)
                    button.setTextColor(Color.BLACK)
                    button.setPadding(24, 40, 25, 40)
                    scrollViewConstraintLayout.addView(button)
                }
            }
        }

    }
}
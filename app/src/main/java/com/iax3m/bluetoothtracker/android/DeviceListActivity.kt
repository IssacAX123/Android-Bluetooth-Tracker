package com.iax3m.bluetoothtracker.android

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import com.iax3m.bluetoothtracker.databinding.ActivityDeviceListBinding

class DeviceListActivity : AppCompatActivity() {
    lateinit var binding:ActivityDeviceListBinding
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeviceListBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

    }
}
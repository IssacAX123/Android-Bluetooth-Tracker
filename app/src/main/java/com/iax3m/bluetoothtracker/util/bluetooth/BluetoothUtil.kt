package com.iax3m.bluetoothtracker.util.bluetooth

import android.Manifest
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.IntentFilter
import kotlin.collections.emptyList
import android.content.pm.PackageManager
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@Module
@InstallIn(ActivityRetainedComponent::class)
class BluetoothUtil(var context: Context) {
    private val _devices= MutableStateFlow<List<BluetoothDevice>>(emptyList())
    val devices: StateFlow<List<BluetoothDevice>>
        get() = _devices.asStateFlow()

    private val bluetoothManager by lazy {
        context.getSystemService(BluetoothManager::class.java)
    }

    private val bluetoothAdapter by lazy {
        bluetoothManager?.adapter
    }

    private val bluetoothReciever = BluetoothReciever{device ->
        _devices.update {devices ->
            val newDevice = BluetoothDevice(device.name, device.address)
            if (newDevice in devices) devices else devices+newDevice

        }

    }


    fun startSearch(){
        if(!hasPermission(Manifest.permission.BLUETOOTH_SCAN)){
            return
        }
        context.registerReceiver(
            bluetoothReciever,
            IntentFilter(android.bluetooth.BluetoothDevice.ACTION_FOUND)
        )
        bluetoothAdapter?.startDiscovery()

    }

    fun stopSearch(){
        if(!hasPermission(Manifest.permission.BLUETOOTH_SCAN)){
            return
        }
        bluetoothAdapter?.cancelDiscovery()

    }

    fun relase(){
        context.unregisterReceiver(bluetoothReciever)

    }

    private fun hasPermission(permission: String): Boolean {
        return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

}
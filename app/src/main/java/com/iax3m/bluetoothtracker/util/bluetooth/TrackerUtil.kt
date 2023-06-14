package com.iax3m.bluetoothtracker.util.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.content.Context
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@SuppressLint("MissingPermission")
@Module
@InstallIn(ActivityRetainedComponent::class)
class TrackerUtil(context: Context): Util(context) {

    private val _rssiState= MutableStateFlow<Int>(0)
    val rssiState: StateFlow<Int>
        get() = _rssiState.asStateFlow()


    fun startRSSIListener(deviceAddress: String){
        // Get the Bluetooth device by its address
        val device: android.bluetooth.BluetoothDevice? = bluetoothAdapter?.getRemoteDevice(deviceAddress)

        // Check if the device is connected
        if (device != null && device.bondState == android.bluetooth.BluetoothDevice.BOND_BONDED) {
            // Get the RSSI value
            device.connectGatt(context, true, object : BluetoothGattCallback() {
                override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
                    super.onConnectionStateChange(gatt, status, newState)
                }

                override fun onReadRemoteRssi(gatt: BluetoothGatt, rssi: Int, status: Int) {
                    if (status == BluetoothGatt.GATT_SUCCESS) {
                        _rssiState.update { rssi }
                    }else{
                        _rssiState.update { 0 }
                    }
                }
            })

        }
    }


}
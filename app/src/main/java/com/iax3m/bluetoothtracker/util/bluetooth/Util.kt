package com.iax3m.bluetoothtracker.util.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@SuppressLint("MissingPermission")
open class Util(var context: Context) {

    private val _connectionState= MutableStateFlow<HashMap<String, Boolean>>(HashMap<String, Boolean>())
    val connectionState: StateFlow<HashMap<String, Boolean>>
        get() = _connectionState.asStateFlow()

    private val bluetoothManager by lazy {
        context.getSystemService(BluetoothManager::class.java)
    }

    val bluetoothAdapter by lazy {
        bluetoothManager?.adapter
    }

    private var bluetoothGatt: BluetoothGatt? = null

    fun setBluetoothConnectionListener(deviceAddress: String){
        // Get the Bluetooth device by its address
        val device: android.bluetooth.BluetoothDevice? = bluetoothAdapter?.getRemoteDevice(deviceAddress)
        device?.let {
            bluetoothGatt = device.connectGatt(context, false, gattCallback.CustomBluetoothGattCallback(deviceAddress))
        }
    }

    private val gattCallback = object : BluetoothGattCallback() {
        inner class CustomBluetoothGattCallback(private val deviceAddress: String) : BluetoothGattCallback() {
            override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
                when (newState) {
                    BluetoothProfile.STATE_CONNECTED -> {
                        _connectionState.update { map->
                            map.put(deviceAddress, true)
                            map}
                    }
                    BluetoothProfile.STATE_DISCONNECTED -> {
                        _connectionState.update { map ->
                            map.put(deviceAddress, true)
                            map}
                    }
                }
            }
        }

    }

    fun disconnect() {
        bluetoothGatt?.disconnect()
        bluetoothGatt?.close()
        bluetoothGatt = null
    }


}
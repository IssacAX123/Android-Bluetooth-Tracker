package com.iax3m.bluetoothtracker.util.bluetooth

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build

@Suppress("DEPRECATION")
class BluetoothReciever(
    private val onDeviceFound: (android.bluetooth.BluetoothDevice) -> Unit
): BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent?.action){
            android.bluetooth.BluetoothDevice.ACTION_FOUND ->{
                val device = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent.getParcelableExtra(
                        android.bluetooth.BluetoothDevice.EXTRA_NAME,
                        android.bluetooth.BluetoothDevice::class.java
                    )
                } else {
                    intent.getParcelableExtra(android.bluetooth.BluetoothDevice.EXTRA_NAME)
                }
                device?.let(onDeviceFound)
            }
        }
    }
}
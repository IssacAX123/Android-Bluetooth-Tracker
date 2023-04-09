package com.iax3m.bluetoothtracker.android

import com.iax3m.bluetoothtracker.util.bluetooth.BluetoothDevice

data class DeviceListUiState(
    val devices: List<BluetoothDevice> = emptyList()
)
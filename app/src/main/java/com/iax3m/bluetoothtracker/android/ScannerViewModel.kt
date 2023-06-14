package com.iax3m.bluetoothtracker.android

import androidx.lifecycle.ViewModel
import com.iax3m.bluetoothtracker.util.bluetooth.TrackerUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScannerViewModel @Inject constructor(
    private val trackerUtil: TrackerUtil
): ViewModel(){

    fun startRSSIListener(deviceAddress: String){
        trackerUtil.startRSSIListener(deviceAddress)
    }

    fun getRSSI(): Int{
        return trackerUtil.rssiState.value
    }

    fun getBluetoothConnectionStatus(){
        trackerUtil.connectionState.value
    }


}
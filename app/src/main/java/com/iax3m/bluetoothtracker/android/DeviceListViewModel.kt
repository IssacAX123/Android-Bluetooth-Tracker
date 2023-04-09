package com.iax3m.bluetoothtracker.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iax3m.bluetoothtracker.util.bluetooth.BluetoothUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DeviceListViewModel @Inject constructor(
    private val bluetoothUtil: BluetoothUtil
): ViewModel() {

    private val _state = MutableStateFlow(DeviceListUiState())
    val state = combine(bluetoothUtil.devices, _state){
        devices, state -> state.copy(devices=devices)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(500), _state.value)

    fun startSearch(){
        bluetoothUtil.startSearch()
    }

    fun stopSearch(){
        bluetoothUtil.stopSearch()
    }
}
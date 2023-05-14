package com.iax3m.bluetoothtracker.di

import android.content.Context
import com.iax3m.bluetoothtracker.util.bluetooth.BluetoothUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {

    @Provides
    fun provideBluetoothUtil(@ApplicationContext app: Context): BluetoothUtil{
        return BluetoothUtil(app)
    }
}
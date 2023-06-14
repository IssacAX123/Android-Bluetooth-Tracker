package com.iax3m.bluetoothtracker.di

import android.content.Context
import com.iax3m.bluetoothtracker.util.bluetooth.BluetoothUtil
import com.iax3m.bluetoothtracker.util.bluetooth.TrackerUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {

    @Provides
    fun provideBluetoothUtil(@ApplicationContext app: Context): BluetoothUtil{
        return BluetoothUtil(app)
    }

    @Provides
    fun provideTrackerUtil(@ApplicationContext app: Context): TrackerUtil {
        return TrackerUtil(app)
    }
}
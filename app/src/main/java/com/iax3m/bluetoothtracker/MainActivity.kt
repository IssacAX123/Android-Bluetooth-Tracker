package com.iax3m.bluetoothtracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.iax3m.bluetoothtracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var mainBtnStart:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view:View = binding.root
        setContentView(view)
        mainBtnStart = binding.mainBtnStart
        mainBtnStart.setOnClickListener(){
            val intent = Intent(this, DeviceListActivity::class.java)
            startActivity(intent)
        }
    }
}
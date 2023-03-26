package com.iax3m.bluetoothtracker.android

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.iax3m.bluetoothtracker.BuildConfig
import com.iax3m.bluetoothtracker.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var mainBtnStart:Button

    private var mInterstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view:View = binding.root
        setContentView(view)

        getAdInterstitionalMainStart()

        MobileAds.initialize(this) { }
        mainBtnStart = binding.mainBtnStart
        mainBtnStart.setOnClickListener(){

            if (mInterstitialAd != null) {
                mInterstitialAd?.show(this)
            }

        }
    }

    fun getAdInterstitionalMainStart(){
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(this, BuildConfig.AD_MAIN_ACTIVITY_INTERSTITIAL, adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
                mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {

                    override fun onAdDismissedFullScreenContent() {
                        val intent = Intent(this@MainActivity, DeviceListActivity::class.java)
                        startActivity(intent)
                        mInterstitialAd = null
                    }

                    override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                        mInterstitialAd = null
                    }
                }
            }
        })
    }
}
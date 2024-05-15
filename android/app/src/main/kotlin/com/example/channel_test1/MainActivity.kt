package com.example.channel_test1


import AirplaneModeChannelHandler
import BatteryChannelHandler
import RestartChannel
import TimeTickChannelHandler
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings




class MainActivity : FlutterActivity() {
    private val REQUEST_OVERLAY_PERMISSION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check if the app can draw overlays
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
                startActivityForResult(intent, REQUEST_OVERLAY_PERMISSION)
            } else {
                // Continue with your app logic if permission is granted
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_OVERLAY_PERMISSION) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    // Permission not granted, inform the user
                } else {
                    // Permission granted, continue with your app logic
                }
            }
        }
    }


    private lateinit var airplaneModeChannelHandler: AirplaneModeChannelHandler



    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)


        //restart
        val restartChannel = RestartChannel(this)
        restartChannel.setupChannel(flutterEngine)
        // battery
        BatteryChannelHandler(this).setupMethodChannel(flutterEngine)
        // time
        TimeTickChannelHandler(this).setupEventChannel(flutterEngine)
        // airplane
        airplaneModeChannelHandler = AirplaneModeChannelHandler(this)
        airplaneModeChannelHandler.setupEventChannel(flutterEngine)

    }


}






package com.example.channel_test1


import AirplaneModeChannelHandler
import BatteryChannelHandler
import RestartChannel
import TimeTickChannelHandler
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine



class MainActivity : FlutterActivity() {

    private lateinit var airplaneModeChannelHandler: AirplaneModeChannelHandler

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)


        val restartChannel = RestartChannel(this)
        restartChannel.setupChannel(flutterEngine)
        BatteryChannelHandler(this).setupMethodChannel(flutterEngine)
        TimeTickChannelHandler(this).setupEventChannel(flutterEngine)

        airplaneModeChannelHandler = AirplaneModeChannelHandler(this)
        airplaneModeChannelHandler.setupEventChannel(flutterEngine)
    }

}






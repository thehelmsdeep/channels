package com.example.channel_test1


import com.example.channel_test.BatteryEventHandler
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.EventChannel

class MainActivity : FlutterActivity() {


    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)



         VolumeControlManager(this, flutterEngine)
//-----------------------------------------------------------------------------------------------------


        val batteryEventHandler = BatteryEventHandler(this)

        // Set up the EventChannel to stream battery updates to Flutter
        val batteryEventChannel =
            EventChannel(flutterEngine.dartExecutor.binaryMessenger, "battery_event_channel")
        batteryEventHandler.setUpEventChannel(batteryEventChannel)

//-----------------------------------------------------------------------------------------------------



    }
}

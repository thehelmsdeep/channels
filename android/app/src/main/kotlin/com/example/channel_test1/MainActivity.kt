package com.example.channel_test1


import android.os.Bundle
import com.example.channel_test.BatteryEventHandler
import com.example.channel_test1.broadcast_receiver.TimeTickService
import com.example.channel_test1.content_provider.contacts.ContactService
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodChannel


class MainActivity : FlutterActivity() {


    private val CHANNEL = "com.example.your_project/time"
    private lateinit var timeTickService: TimeTickService

    private lateinit var contactService: ContactService

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)


 //-----------------------------------------------------------------------------------------------------broadcast receiver

        // Create the MethodChannel and pass it to the MethodChannelHandler
        val methodChannel = MethodChannel(flutterEngine.dartExecutor, CHANNEL)

        // Initialize TimeTickService and call setup() to register receiver
        timeTickService = TimeTickService(this, methodChannel)
        timeTickService.setup()

        // Set up the MethodChannelHandler to handle method calls
        MethodChannelHandler(methodChannel)


//-----------------------------------------------------------------------------------------------------old codes

        VolumeControlManager(this, flutterEngine)


        val batteryEventHandler = BatteryEventHandler(this)

        // Set up the EventChannel to stream battery updates to Flutter
        val batteryEventChannel =
            EventChannel(flutterEngine.dartExecutor.binaryMessenger, "battery_event_channel")
        batteryEventHandler.setUpEventChannel(batteryEventChannel)

//-----------------------------------------------------------------------------------------------------content provider

        contactService = ContactService(contentResolver)
        contactService.setup(flutterEngine)



    }


    override fun onDestroy() {
        super.onDestroy()
        timeTickService.tearDown() // Unregister the receiver when the activity is destroyed
    }


}

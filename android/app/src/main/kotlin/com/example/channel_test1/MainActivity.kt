package com.example.channel_test1


import com.example.channel_test.event_channel.BatteryEventHandler
import com.example.channel_test1.event_channels.VolumeControlManager
import com.example.channel_test1.method_channels.DeviceInfoChannel
import com.example.channel_test1.method_channels.content_resolver.ContactService
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.EventChannel


class MainActivity : FlutterActivity() {



    private lateinit var contactService: ContactService




    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)


//--------------------------------------------------------------------------------method channels

        DeviceInfoChannel.registerWith(flutterEngine)


//--------------------------------------------------------------------------------event channels
        VolumeControlManager(this, flutterEngine)

        val batteryEventHandler = BatteryEventHandler(this)
        val batteryEventChannel = EventChannel(flutterEngine.dartExecutor.binaryMessenger, "channels/battery")
        batteryEventHandler.setUpEventChannel(batteryEventChannel)


//--------------------------------------------------------------------------------content providers
        contactService = ContactService(contentResolver)
        contactService.setup(flutterEngine)

    }


    override fun onDestroy() {
        super.onDestroy()

    }


}

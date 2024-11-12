package com.example.channel_test1


import com.example.channel_test.event_channel.BatteryEventHandler
import com.example.channel_test1.broadcast_receiver.TimeTickService
import com.example.channel_test1.content_provider.contacts.ContactService
import com.example.channel_test1.event_channel.VolumeControlManager
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodChannel


class MainActivity : FlutterActivity() {


    private lateinit var timeTickService: TimeTickService
    private lateinit var channelHandler: MethodChannelHandler
    private lateinit var contactService: ContactService



    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)


//broadcast receivers

        channelHandler = MethodChannelHandler(flutterEngine)
        timeTickService = TimeTickService(this, channelHandler,"channels/time_tick_channel")
        timeTickService.setup()




//event channels

        VolumeControlManager(this, flutterEngine)

        val batteryEventHandler = BatteryEventHandler(this)
        val batteryEventChannel = EventChannel(flutterEngine.dartExecutor.binaryMessenger, "channels/battery_event_channel")
        batteryEventHandler.setUpEventChannel(batteryEventChannel)




//content providers

        contactService = ContactService(contentResolver)
        contactService.setup(flutterEngine)

    }


    override fun onDestroy() {
        super.onDestroy()
        timeTickService.tearDown()
    }


}

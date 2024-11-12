package com.example.channel_test1


import android.content.IntentFilter
import android.net.ConnectivityManager
import com.example.channel_test.event_channel.BatteryEventHandler
import com.example.channel_test1.content_provider.contacts.ContactService
import com.example.channel_test1.event_channel.VolumeControlManager
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.android.KeyData.CHANNEL
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodChannel


class MainActivity : FlutterActivity() {



    private lateinit var contactService: ContactService




    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)


//--------------------------------------------------------------------------------broadcast receivers




//--------------------------------------------------------------------------------event channels
        VolumeControlManager(this, flutterEngine)

        val batteryEventHandler = BatteryEventHandler(this)
        val batteryEventChannel = EventChannel(flutterEngine.dartExecutor.binaryMessenger, "channels/battery_event_channel")
        batteryEventHandler.setUpEventChannel(batteryEventChannel)


//--------------------------------------------------------------------------------content providers
        contactService = ContactService(contentResolver)
        contactService.setup(flutterEngine)

    }


    override fun onDestroy() {
        super.onDestroy()

    }


}

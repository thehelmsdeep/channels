package com.example.channel_test

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import io.flutter.plugin.common.EventChannel

class BatteryEventHandler(private val context: Context) {

    private var batteryReceiver: BroadcastReceiver? = null

    // Method to set up the EventChannel stream handler
    fun setUpEventChannel(eventChannel: EventChannel) {
        eventChannel.setStreamHandler(object : EventChannel.StreamHandler {
            // Called when Flutter starts listening to the stream
            override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
                // Register the BroadcastReceiver to listen for battery level changes
                batteryReceiver = object : BroadcastReceiver() {
                    override fun onReceive(context: Context, intent: Intent) {
                        val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                        events?.success(level) // Send the battery level to Flutter
                    }
                }

                // Register the receiver to listen for battery changes
                val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
                context.registerReceiver(batteryReceiver, filter)
            }

            // Called when Flutter stops listening to the stream
            override fun onCancel(arguments: Any?) {
                // Unregister the BroadcastReceiver to stop listening for battery changes
                batteryReceiver?.let {
                    context.unregisterReceiver(it)
                }
                batteryReceiver = null
            }
        })
    }
}

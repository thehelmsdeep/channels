package com.example.channel_test.event_channel

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import io.flutter.plugin.common.EventChannel

class BatteryEventHandler(private val context: Context) {

    private var batteryReceiver: BroadcastReceiver? = null

    fun setUpEventChannel(eventChannel: EventChannel) {
        eventChannel.setStreamHandler(object : EventChannel.StreamHandler {
            override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
                batteryReceiver = object : BroadcastReceiver() {
                    override fun onReceive(context: Context, intent: Intent) {
                        val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                        events?.success(level)
                    }
                }

                val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
                context.registerReceiver(batteryReceiver, filter)
            }

            override fun onCancel(arguments: Any?) {
                batteryReceiver?.let {
                    context.unregisterReceiver(it)
                }
                batteryReceiver = null
            }
        })
    }
}

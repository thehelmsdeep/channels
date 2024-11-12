package com.example.channel_test.event_channel

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.EventChannel

class BatteryEventHandler(private val context: Context, flutterEngine: FlutterEngine) {


    init {
        EventChannel(flutterEngine.dartExecutor.binaryMessenger, "battery_channel")
            .setStreamHandler(object : EventChannel.StreamHandler {

                private var events: EventChannel.EventSink? = null

                private val batteryReceiver = object : BroadcastReceiver() {
                    override fun onReceive(context: Context, intent: Intent) {
                        val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                        events?.success(level)  // Send the level to Flutter
                    }
                }

                override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
                    this.events = events
                    val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
                    context.registerReceiver(batteryReceiver, filter)
                }

                override fun onCancel(arguments: Any?) {
                    context.unregisterReceiver(batteryReceiver)
                }
            })
    }
}

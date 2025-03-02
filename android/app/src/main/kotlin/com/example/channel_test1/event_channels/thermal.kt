package com.example.channel_test1.event_channels

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Handler
import android.os.Looper
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodChannel

class ThermalManager(context: Context, flutterEngine: FlutterEngine) {

    private var currentTemperature: Float = getBatteryTemperature(context)

    init {
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, "channels/method/thermal").setMethodCallHandler { call, result ->
            when (call.method) {
                "getBatteryTemperature" -> result.success(currentTemperature)
                else -> result.notImplemented()
            }
        }

        EventChannel(flutterEngine.dartExecutor.binaryMessenger, "channels/event/thermal").setStreamHandler(object : EventChannel.StreamHandler {
            private var events: EventChannel.EventSink? = null
            private val temperatureObserver = object : Runnable {
                override fun run() {
                    val newTemperature = getBatteryTemperature(context)
                    if (newTemperature != currentTemperature) {
                        currentTemperature = newTemperature
                        events?.success("Battery Temperature: $currentTemperature°C")
                    }
                    Handler(Looper.getMainLooper()).postDelayed(this, 1000)  // Check every second
                }
            }

            override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
                this.events = events
                events?.success("Battery Temperature: $currentTemperature°C")
                // Start observing temperature changes
                Handler(Looper.getMainLooper()).post(temperatureObserver)
            }

            override fun onCancel(arguments: Any?) {
                // Stop observing
                Handler(Looper.getMainLooper()).removeCallbacks(temperatureObserver)
            }
        })
    }

    // Function to get the battery temperature using Intent
    private fun getBatteryTemperature(context: Context): Float {
        val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { filter ->
            context.registerReceiver(null, filter)
        }

        // Extract temperature in tenths of a degree Celsius and convert to Celsius
        return batteryStatus?.let {
            val temperature = it.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)
            temperature / 10f // Convert tenths of a degree to Celsius
        } ?: -1f  // Return -1f if unable to get temperature
    }
}

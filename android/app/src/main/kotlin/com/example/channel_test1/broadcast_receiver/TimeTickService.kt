package com.example.channel_test1.broadcast_receiver


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import io.flutter.plugin.common.MethodChannel
import java.text.SimpleDateFormat
import java.util.*

class TimeTickService(private val context: Context, private val channel: MethodChannel) {

    private lateinit var timeTickReceiver: BroadcastReceiver

    // Setup method to initialize the TimeTick receiver
    fun setup() {
        timeTickReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == Intent.ACTION_TIME_TICK) {
                    // Get current time when the event is triggered
                    val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())

                    // Create the event details to send to Flutter
                    val eventDetails = mapOf(
                        "event" to "TIME_TICK",
                        "currentTime" to currentTime,
                        "status" to "active"
                    )

                    // Send the data to Flutter
                    Log.d("TimeTickService", "Sending event details: $eventDetails")
                    channel.invokeMethod("onTimeTick", eventDetails)
                }
            }
        }

        // Register the receiver dynamically for TIME_TICK broadcast
        val filter = IntentFilter(Intent.ACTION_TIME_TICK)
        context.registerReceiver(timeTickReceiver, filter)
    }

    // Tear down the receiver when the service is no longer needed
    fun tearDown() {
        context.unregisterReceiver(timeTickReceiver)
    }
}

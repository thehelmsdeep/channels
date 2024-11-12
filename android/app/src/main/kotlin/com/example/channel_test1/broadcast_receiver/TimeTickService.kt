package com.example.channel_test1.broadcast_receiver



import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import com.example.channel_test1.MethodChannelHandler
import java.text.SimpleDateFormat
import java.util.*

class TimeTickService(
    private val context: Context,
    private val channelHandler: MethodChannelHandler,
    private val channelName: String
) {

    private lateinit var timeTickReceiver: BroadcastReceiver

    fun setup() {
        channelHandler.registerChannel(channelName) { call, result ->
            when (call.method) {
                "startListening" -> {
                    setupTimeTickReceiver()
                    result.success("TimeTickService started")
                }
                "stopListening" -> {
                    tearDown()
                    result.success("TimeTickService stopped")
                }
                else -> result.notImplemented()
            }
        }
    }

    private fun setupTimeTickReceiver() {
        timeTickReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == Intent.ACTION_TIME_TICK) {
                    val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())

                    val eventDetails = mapOf(
                        "event" to "TIME_TICK",
                        "currentTime" to currentTime,
                        "status" to "active"
                    )

                    Log.d("TimeTickService", "Sending event details: $eventDetails")

                    // Send the event to Flutter via the dynamic channel handler
                    channelHandler.sendEvent(channelName, "onTimeTick", eventDetails)
                }
            }
        }

        val filter = IntentFilter(Intent.ACTION_TIME_TICK)
        context.registerReceiver(timeTickReceiver, filter)
    }

    fun tearDown() {
        context.unregisterReceiver(timeTickReceiver)
    }
}

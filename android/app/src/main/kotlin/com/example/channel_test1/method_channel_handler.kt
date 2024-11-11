package com.example.channel_test1


import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

class MethodChannelHandler(private val channel: MethodChannel) : MethodCallHandler {

    init {
        channel.setMethodCallHandler(this) // Set the handler to handle method calls
    }

    // This method will handle incoming method calls from Flutter
    override fun onMethodCall(call: MethodCall, result: Result) {
        when (call.method) {
            "getTimeTick" -> {
                // Respond to Flutter request if needed
                result.success("TIME_TICK Broadcast ready!")
            }
            else -> {
                result.notImplemented() // Handle unsupported method calls
            }
        }
    }
}

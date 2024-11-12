package com.example.channel_test1

import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodCall

class MethodChannelHandler(private val flutterEngine: FlutterEngine) {

    private val channels = mutableMapOf<String, MethodChannel>()

    fun registerChannel(channelName: String, handler: (MethodCall, MethodChannel.Result) -> Unit) {
        if (channels.containsKey(channelName)) {
            channels[channelName]?.setMethodCallHandler(null)
        }

        val methodChannel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, channelName)
        methodChannel.setMethodCallHandler { call, result ->
            handler(call, result)
        }

        channels[channelName] = methodChannel
    }

    fun unregisterChannel(channelName: String) {
        channels[channelName]?.setMethodCallHandler(null)
        channels.remove(channelName)
    }

    fun dispose() {
        channels.values.forEach { it.setMethodCallHandler(null) }
        channels.clear()
    }

    // Method to send events to Flutter from a specific channel
    fun sendEvent(channelName: String, method: String, arguments: Any?) {
        channels[channelName]?.invokeMethod(method, arguments)
    }
}

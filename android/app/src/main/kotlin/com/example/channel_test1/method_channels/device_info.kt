package com.example.channel_test1.method_channels


import android.os.Build
import io.flutter.plugin.common.MethodChannel
import io.flutter.embedding.engine.FlutterEngine

object DeviceInfoChannel {
    private const val CHANNEL = "channels/device_info"

    fun registerWith(flutterEngine: FlutterEngine) {
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler { call, result ->
            when (call.method) {
                "getDeviceInfo" -> {
                    val deviceInfo = getDeviceInfo()
                    result.success(deviceInfo)
                }
                else -> result.notImplemented()
            }
        }
    }

    private fun getDeviceInfo(): Map<String, String> {
        return mapOf(
            "MODEL" to Build.MODEL,
            "MANUFACTURER" to Build.MANUFACTURER,
            "VERSION" to Build.VERSION.RELEASE,
            "SDK_INT" to Build.VERSION.SDK_INT.toString(),
            "DEVICE" to Build.DEVICE,
            "ID" to Build.ID,
            "BOARD" to Build.BOARD,
            "DISPLAY" to Build.DISPLAY,
            "HARDWARE" to Build.HARDWARE

        )
    }
}

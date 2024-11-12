package com.example.channel_test1.event_channels

import android.content.Context
import android.media.AudioManager
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodChannel

class VolumeControlManager(context: Context, flutterEngine: FlutterEngine) {

    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    private var currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

    init {
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, "channels/method/volume").setMethodCallHandler { call, result ->
            when (call.method) {
                "getMaxVolume" -> result.success(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC))
                "getCurrentVolume" -> result.success(currentVolume)
                "setVolume" -> { audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, call.arguments as Int, 0); result.success(null) }
                else -> result.notImplemented()
            }
        }

        EventChannel(flutterEngine.dartExecutor.binaryMessenger, "channels/event/volume").setStreamHandler(object : EventChannel.StreamHandler {
            private var events: EventChannel.EventSink? = null
            private val volumeObserver = object : android.database.ContentObserver(Handler(Looper.getMainLooper())) {
                override fun onChange(selfChange: Boolean) {
                    val newVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
                    if (newVolume != currentVolume) { currentVolume = newVolume; events?.success(currentVolume) }
                }
            }

            override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
                this.events = events
                events?.success(currentVolume)
                context.contentResolver.registerContentObserver(Settings.System.CONTENT_URI, true, volumeObserver)
            }

            override fun onCancel(arguments: Any?) {
                context.contentResolver.unregisterContentObserver(volumeObserver)
            }
        })
    }
}

package com.example.channel_test1



import android.content.Context
import android.database.ContentObserver
import android.media.AudioManager
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodChannel

class VolumeControlManager(private val context: Context, flutterEngine: FlutterEngine) {

    private val methodChannelName = "volume_control_channel"
    private val eventChannelName = "volume_event_channel"
    private val audioManager: AudioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    private var streamVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
    private var volumeObserver: ContentObserver? = null

    init {
        // Set up MethodChannel
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, methodChannelName).setMethodCallHandler { call, result ->
            when (call.method) {
                "getMaxVolume" -> result.success(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC))
                "getCurrentVolume" -> result.success(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC))
                "setVolume" -> {
                    val volumeLevel = call.arguments as Int
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volumeLevel, 0)
                    result.success(null)
                }
                else -> result.notImplemented()
            }
        }

        // Set up EventChannel for listening to volume changes
        EventChannel(flutterEngine.dartExecutor.binaryMessenger, eventChannelName).setStreamHandler(object : EventChannel.StreamHandler {
            override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
                // Send initial volume
                events?.success(streamVolume)

                // Set up ContentObserver to track volume changes
                volumeObserver = object : ContentObserver(Handler(Looper.getMainLooper())) {
                    override fun onChange(selfChange: Boolean) {
                        val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
                        if (currentVolume != streamVolume) {
                            streamVolume = currentVolume
                            events?.success(streamVolume) // Send updated volume to Flutter
                        }
                    }
                }
                context.contentResolver.registerContentObserver(
                    Settings.System.CONTENT_URI,
                    true,
                    volumeObserver as ContentObserver
                )
            }

            override fun onCancel(arguments: Any?) {
                // Unregister the ContentObserver to stop listening
                if (volumeObserver != null) {
                    context.contentResolver.unregisterContentObserver(volumeObserver as ContentObserver)
                    volumeObserver = null
                }
            }
        })
    }
}

package com.example.channel_test1


import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine

class MainActivity : FlutterActivity() {

    private lateinit var volumeControlManager: VolumeControlManager

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        // Initialize VolumeControlManager
        volumeControlManager = VolumeControlManager(this, flutterEngine)

    }
}

package com.example.channel_test1


import android.widget.Toast
import com.example.channel_test.event_channel.BatteryEventHandler
import com.example.channel_test1.event_channels.VolumeControlManager
import com.example.channel_test1.method_channels.DeviceInfoChannel
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import android.Manifest
import com.example.channel_test1.method_channels.content_providers.ContactServicee
import com.example.channel_test1.event_channels.ThermalManager


class MainActivity : FlutterActivity() {


    private lateinit var contactService: ContactServicee
    private lateinit var permissionHandler: PermissionHandler
    private val CAMERA_PERMISSION_CODE = 100


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionHandler.onRequestPermissionsResult(requestCode, grantResults)
    }



    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)


        permissionHandler = PermissionHandler(this)

        permissionHandler.requestPermission(
            Manifest.permission.CAMERA,
            CAMERA_PERMISSION_CODE
        ) { isGranted ->
            if (isGranted) {
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    this,
                    "Camera permission is required to use this feature",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }






///-------------------------------------------------------------
///                      Method channel
///-------------------------------------------------------------


        contactService = ContactServicee(contentResolver)
        contactService.setup(flutterEngine)


        DeviceInfoChannel.registerWith(flutterEngine)

///-------------------------------------------------------------
///                      Event channel
///-------------------------------------------------------------


        VolumeControlManager(this, flutterEngine)

        BatteryEventHandler(this, flutterEngine)

        ThermalManager(this, flutterEngine)



    }






}

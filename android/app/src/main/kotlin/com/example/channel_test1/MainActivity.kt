package com.example.channel_test1


import android.widget.Toast
import com.example.channel_test.event_channel.BatteryEventHandler
import com.example.channel_test1.event_channels.VolumeControlManager
import com.example.channel_test1.method_channels.DeviceInfoChannel
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import android.Manifest
import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import com.example.channel_test1.method_channels.content_providers.ContactServicee
import android.provider.Settings
import android.text.TextUtils
import android.accessibilityservice.AccessibilityService
import android.app.AlertDialog
import android.view.accessibility.AccessibilityManager


class MainActivity : FlutterActivity() {



    private lateinit var contactService: ContactServicee
    private lateinit var permissionHandler: PermissionHandler
    private val CAMERA_PERMISSION_CODE = 100





    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionHandler.onRequestPermissionsResult(requestCode, grantResults)
    }





    // Redirect the user to the accessibility settings
    private fun openAccessibilitySettings(context: Context) {
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        context.startActivity(intent)
    }

    // Show a dialog to explain why the service needs to be enabled
    private fun showAccessibilityServiceDialog(context: Context) {
        val dialog = AlertDialog.Builder(context)
            .setTitle("Enable Accessibility Service")
            .setMessage("To monitor when the Phone/Dialer app is opened, you need to enable the accessibility service in the Accessibility Settings.")
            .setPositiveButton("Go to Settings") { _, _ ->
                openAccessibilitySettings(context)  // Redirect to Accessibility Settings
            }
            .setNegativeButton("Cancel", null)
            .show()
    }






    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)


        permissionHandler = PermissionHandler(this)

        permissionHandler.requestPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE) { isGranted ->
            if (isGranted) {
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Camera permission is required to use this feature", Toast.LENGTH_SHORT).show()
            }
        }


        showAccessibilityServiceDialog(this)








        DeviceInfoChannel.registerWith(flutterEngine)


        VolumeControlManager(this, flutterEngine)

        BatteryEventHandler(this, flutterEngine)


        contactService = ContactServicee(contentResolver)
        contactService.setup(flutterEngine)

    }


    override fun onDestroy() {
        super.onDestroy()
    }





}

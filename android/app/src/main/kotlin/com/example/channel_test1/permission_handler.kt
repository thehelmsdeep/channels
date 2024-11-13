package com.example.channel_test1


import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

typealias PermissionCallback = (Boolean) -> Unit

class PermissionHandler(private val activity: Activity) {

    // Store callbacks for each request
    private val callbacks = mutableMapOf<Int, PermissionCallback>()

    // Check if a permission is granted
    fun isPermissionGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED
    }

    // Request a single permission
    fun requestPermission(permission: String, requestCode: Int, callback: PermissionCallback) {
        if (isPermissionGranted(permission)) {
            // If permission is already granted, execute callback with true
            callback(true)
        } else {
            // Store the callback and request permission
            callbacks[requestCode] = callback
            ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
        }
    }

    // Request multiple permissions
    fun requestPermissions(permissions: Array<String>, requestCode: Int, callback: PermissionCallback) {
        val deniedPermissions = permissions.filter { !isPermissionGranted(it) }
        if (deniedPermissions.isEmpty()) {
            // All permissions are granted, execute callback with true
            callback(true)
        } else {
            // Store the callback and request the denied permissions
            callbacks[requestCode] = callback
            ActivityCompat.requestPermissions(activity, deniedPermissions.toTypedArray(), requestCode)
        }
    }

    // Call this function from the activity's onRequestPermissionsResult
    fun onRequestPermissionsResult(requestCode: Int, grantResults: IntArray) {
        val isGranted = grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }
        callbacks[requestCode]?.invoke(isGranted)  // Execute callback with result
        callbacks.remove(requestCode)  // Remove the callback after use
    }
}

package com.example.channel_test1

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log



class BootReceiver : BroadcastReceiver() {


    private val tag = "BootReceiver"

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(tag, "BootReceiver received broadcast: ${intent.action}")

        if (intent.action == Intent.ACTION_BOOT_COMPLETED){
            Log.d(tag, "Device Boot Completed")
        }

    }


}

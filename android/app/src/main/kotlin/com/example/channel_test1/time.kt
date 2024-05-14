import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.EventChannel
import java.util.*

// receiver with channel

class TimeTickChannelHandler(private val context: Context) {

    private val channel = "asnprz/time"

    fun setupEventChannel(flutterEngine: FlutterEngine) {
        val eventChannel = EventChannel(flutterEngine.dartExecutor.binaryMessenger, channel)

        eventChannel.setStreamHandler(object : EventChannel.StreamHandler {
            private var timeTickReceiver: BroadcastReceiver? = null

            override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
                timeTickReceiver = createTimeTickReceiver(events)
                registerTimeTickReceiver(timeTickReceiver)
            }

            override fun onCancel(arguments: Any?) {
                unregisterTimeTickReceiver(timeTickReceiver)
                timeTickReceiver = null
            }
        })
    }



    private fun createTimeTickReceiver(eventSink: EventChannel.EventSink?): BroadcastReceiver {
        return object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == Intent.ACTION_TIME_TICK || intent?.action == Intent.ACTION_TIME_CHANGED) {
                    val currentTime = Calendar.getInstance()
                    val hour = currentTime.get(Calendar.HOUR_OF_DAY)
                    val minute = currentTime.get(Calendar.MINUTE)
                    val second = currentTime.get(Calendar.SECOND)
                    val timeString = "$hour:$minute:$second"
                    eventSink?.success(timeString)
                }
            }
        }
    }


    private fun registerTimeTickReceiver(receiver: BroadcastReceiver?) {
        context.registerReceiver(receiver, IntentFilter(Intent.ACTION_TIME_TICK))
    }

    private fun unregisterTimeTickReceiver(receiver: BroadcastReceiver?) {
        context.unregisterReceiver(receiver)
    }
}

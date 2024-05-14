
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.widget.Toast
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.EventChannel


// receiver with channel

class AirplaneModeChannelHandler(private val context: Context) {

    private val channel = "asnprz/airplane_mode"

    fun setupEventChannel(flutterEngine: FlutterEngine) {
        val eventChannel = EventChannel(flutterEngine.dartExecutor.binaryMessenger, channel)

        eventChannel.setStreamHandler(object : EventChannel.StreamHandler {
            private var airplaneModeReceiver: BroadcastReceiver? = null

            override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
                airplaneModeReceiver = createAirplaneModeReceiver(events)
                registerAirplaneModeReceiver(airplaneModeReceiver)
            }

            override fun onCancel(arguments: Any?) {
                unregisterAirplaneModeReceiver(airplaneModeReceiver)
                airplaneModeReceiver = null
            }
        })
    }

    private fun createAirplaneModeReceiver(eventSink: EventChannel.EventSink?): BroadcastReceiver {
        return object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
                    val isAirplaneModeOn = intent.getBooleanExtra("state", false)
                    Toast.makeText(context, "$isAirplaneModeOn", Toast.LENGTH_LONG).show()
                    eventSink?.success(isAirplaneModeOn)
                }
            }
        }
    }

    private fun registerAirplaneModeReceiver(receiver: BroadcastReceiver?) {
        val filter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        context.registerReceiver(receiver, filter)
    }

    private fun unregisterAirplaneModeReceiver(receiver: BroadcastReceiver?) {
        context.unregisterReceiver(receiver)
    }
}

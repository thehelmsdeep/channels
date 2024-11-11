package com.example.channel_test1



import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

class MethodChannelHandler(private val channel: MethodChannel) : MethodCallHandler {

    // Map to store dynamically added methods and their corresponding actions
    private val methodHandlers = mutableMapOf<String, (MethodCall, Result) -> Unit>()

    init {
        channel.setMethodCallHandler(this)
    }

    override fun onMethodCall(call: MethodCall, result: Result) {
        // Look up the method name in the map and execute it if found
        methodHandlers[call.method]?.invoke(call, result) ?: result.notImplemented()
    }

    // Function to add a new method handler
    fun registerMethod(methodName: String, handler: (MethodCall, Result) -> Unit) {
        methodHandlers[methodName] = handler
    }

    // Function to remove a method handler
    fun unregisterMethod(methodName: String) {
        methodHandlers.remove(methodName)
    }
}



EventChannels are used for pushing events from native to Flutter.


EventChannel is for Flutter to listen to ongoing data streams
or events that are emitted by the native code.




Unlike MethodChannel,
EventChannels do not directly support sending data from Flutter back to native code in the same way.
If you need to invoke a method on the native side and expect a response, you would use a MethodChannel.
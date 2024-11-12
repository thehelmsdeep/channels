import 'package:flutter/services.dart';


typedef MethodCallHandler = Future<dynamic> Function(MethodCall call);


class MethodChannelHandler {
  final String channelName;
  late MethodChannel _channel;

  MethodChannelHandler(this.channelName) {
    _channel = MethodChannel(channelName);
  }

  // Set up the handler for method calls
  void setMethodCallHandler(MethodCallHandler handler) {
    _channel.setMethodCallHandler(handler);
  }

  Future<dynamic> invokeMethod(String method, [dynamic arguments]) async {
    try {
      return await _channel.invokeMethod(method, arguments);
    } on PlatformException catch (e) {
      print("Error calling method $method: ${e.message}");
      return null;
    }
  }

  // Clean up method handler
  void clearHandler() {
    _channel.setMethodCallHandler(null);
  }

  void unregisterChannel() {
    _channel.setMethodCallHandler(null);
    print("Unregistered channel: $channelName");
  }

  void dispose() {
    unregisterChannel();
    print("Disposed channel: $channelName");
  }
}

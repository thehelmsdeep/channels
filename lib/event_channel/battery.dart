import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class BatteryLevelScreen extends StatefulWidget {
  @override
  _BatteryLevelScreenState createState() => _BatteryLevelScreenState();
}

class _BatteryLevelScreenState extends State<BatteryLevelScreen> {
  static const EventChannel _batteryEventChannel =
  EventChannel('battery_event_channel');
  int _batteryLevel = 0;

  @override
  void initState() {
    super.initState();

    _batteryEventChannel.receiveBroadcastStream().listen(
          (batteryLevel) {
        setState(() {
          _batteryLevel = batteryLevel;
        });
      },
      onError: (error) {
        print("Error receiving battery level: $error");
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("Battery Level")),
      body: Center(
        child: Text(
          'Battery Level: $_batteryLevel%',
          style: TextStyle(fontSize: 24),
        ),
      ),
    );
  }
}

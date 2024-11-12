import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: BatteryLevelScreen(),
    );
  }
}

class BatteryLevelScreen extends StatefulWidget {
  @override
  _BatteryLevelScreenState createState() => _BatteryLevelScreenState();
}

class _BatteryLevelScreenState extends State<BatteryLevelScreen> {
  static const batteryChannel = EventChannel('battery_channel');
  int _batteryLevel = -1;

  @override
  void initState() {
    super.initState();
    batteryChannel.receiveBroadcastStream().listen(
          (event) {
        setState(() {
          _batteryLevel = event as int;
        });
      },
      onError: (error) {
        print("Error: $error");
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("Battery Level")),
      body: Center(
        child: _batteryLevel == -1
            ? CircularProgressIndicator()
            : Text("Battery Level: $_batteryLevel%"),
      ),
    );
  }
}

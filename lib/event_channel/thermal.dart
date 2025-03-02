import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: BatteryTemperatureScreen(),
    );
  }
}

class BatteryTemperatureScreen extends StatefulWidget {
  @override
  _BatteryTemperatureScreenState createState() =>
      _BatteryTemperatureScreenState();
}

class _BatteryTemperatureScreenState extends State<BatteryTemperatureScreen> {
  static const platformMethodChannel = MethodChannel('channels/method/thermal');
  static const platformEventChannel = EventChannel('channels/event/thermal');

  String _batteryTemperature = "Loading...";

  @override
  void initState() {
    super.initState();
    _getBatteryTemperature();
    _startTemperatureUpdates();
  }


  // Fetch initial battery temperature
  Future<void> _getBatteryTemperature() async {
    try {
      final double temperature =
      await platformMethodChannel.invokeMethod('getBatteryTemperature');
      setState(() {
        _batteryTemperature = '$temperature°C';
      });
    } on PlatformException catch (e) {
      setState(() {
        _batteryTemperature = 'Failed to get battery temperature: ${e.message}';
      });
    }
  }



  // Start listening for battery temperature updates
  void _startTemperatureUpdates() {
    platformEventChannel.receiveBroadcastStream().listen((dynamic event) {
      setState(() {
        _batteryTemperature = event;  // Update temperature
      });
    }, onError: (error) {
      setState(() {
        _batteryTemperature = 'Error receiving temperature updates: $error';
      });
    });
  }



  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Battery Temperature'),
      ),
      body: Center(
        child: Text(
          'Battery Temperature: $_batteryTemperature',
          style: TextStyle(fontSize: 24),
        ),
      ),
    );
  }
}

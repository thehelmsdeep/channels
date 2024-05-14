import 'package:flutter/material.dart';
import 'package:flutter/services.dart';


class Airplane extends StatefulWidget {
  @override
  _AirplaneState createState() => _AirplaneState();
}

class _AirplaneState extends State<Airplane> {
  static const platform = EventChannel('asnprz/airplane_mode');
  bool isAirplaneModeOn = false;

  @override
  void initState() {
    super.initState();
    platform.receiveBroadcastStream().listen(_onAirplaneModeChanged);
  }

  void _onAirplaneModeChanged(dynamic state) {
    setState(() {
      isAirplaneModeOn = state;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        body: Center(
          child: Text('Airplane Mode is ${isAirplaneModeOn ? 'ON' : 'OFF'}'),
        ),
      ),
    );
  }
}

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';





class TimeTickWidget extends StatefulWidget {
  @override
  _TimeTickWidgetState createState() => _TimeTickWidgetState();
}

class _TimeTickWidgetState extends State<TimeTickWidget> {
  // Define an event channel name to match the one set in the Kotlin class
  static const EventChannel _eventChannel =
  EventChannel('asnprz/time');

  String _currentTime = 'Waiting for time tick...';

  @override
  void initState() {
    super.initState();
    _eventChannel.receiveBroadcastStream().listen(_updateTime);
  }

  void _updateTime(dynamic currentTime) {
    setState(() {
      _currentTime = currentTime.toString();
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text(
              'Current Time:',
              style: TextStyle(fontSize: 20),
            ),
            SizedBox(height: 10),
            Text(
              _currentTime,
              style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
            ),
          ],
        ),
      ),
    );
  }

  @override
  void dispose() {
    super.dispose();
  }
}

import 'package:flutter/services.dart';
import 'package:flutter/material.dart';




void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Time Tick Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: TimeTickPage(),
    );
  }
}




class TimeTickPage extends StatefulWidget {
  @override
  _TimeTickPageState createState() => _TimeTickPageState();
}

class _TimeTickPageState extends State<TimeTickPage> {
  final TimeTickListener _timeTickListener = TimeTickListener();
  String _currentTime = "N/A";
  bool _isListening = false;

  @override
  void initState() {
    super.initState();

    _timeTickListener.listenToTimeTicks((eventDetails) {
      setState(() {
        _currentTime = eventDetails['currentTime'] ?? "N/A";
      });
    });
  }

  void _toggleListening() async {
    if (_isListening) {
      await _timeTickListener.stopListening();
    } else {
      await _timeTickListener.startListening();
    }

    setState(() {
      _isListening = !_isListening;
    });
  }

  @override
  void dispose() {
    _timeTickListener.stopListening();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Time Tick Demo'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Text(
              'Current Time:',
              style: TextStyle(fontSize: 24),
            ),
            const SizedBox(height: 10),
            Text(
              _currentTime,
              style: const TextStyle(fontSize: 36, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 30),
            ElevatedButton(
              onPressed: _toggleListening,
              child: Text(_isListening ? 'Stop Listening' : 'Start Listening'),
            ),
          ],
        ),
      ),
    );
  }
}





class TimeTickListener {
  static const _channel = MethodChannel('channels/time_tick_channel');

  Future<void> startListening() async {
    try {
      await _channel.invokeMethod('startListening');
    } catch (e) {
      print("Error starting time tick listener: $e");
    }
  }

  Future<void> stopListening() async {
    try {
      await _channel.invokeMethod('stopListening');
    } catch (e) {
      print("Error stopping time tick listener: $e");
    }
  }

  void listenToTimeTicks(Function(Map<String, dynamic>) callback) {
    _channel.setMethodCallHandler((call) async {
      if (call.method == 'onTimeTick') {
        final Map<String, dynamic> eventDetails = Map<String, dynamic>.from(call.arguments);
        callback(eventDetails);
      }
    });
  }
}

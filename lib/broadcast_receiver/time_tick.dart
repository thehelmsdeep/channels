import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  static const platform = MethodChannel('com.example.your_project/time');

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter TimeTick',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  String _message = "Waiting for TimeTick...";

  @override
  void initState() {
    super.initState();

    // Listen for MethodChannel call from Android
    MyApp.platform.setMethodCallHandler((call) async {
      if (call.method == "onTimeTick") {
        // Handle the received data
        final eventData = Map<String, dynamic>.from(call.arguments);
        setState(() {
          _message = "Event: ${eventData['event']}\nTime: ${eventData['currentTime']}\nStatus: ${eventData['status']}";
        });
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Flutter TimeTick Example"),
      ),
      body: Center(
        child: Text(
          _message,
          style: TextStyle(fontSize: 24),
        ),
      ),
    );
  }
}

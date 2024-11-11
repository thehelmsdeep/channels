import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  static const platform = MethodChannel('channels/time');

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

    MyApp.platform.setMethodCallHandler((call) async {
      if (call.method == "onTimeTick") {
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
        title: const Text("Flutter TimeTick Example"),
      ),
      body: Center(
        child: Text(
          _message,
          style: const TextStyle(fontSize: 24),
        ),
      ),
    );
  }
}

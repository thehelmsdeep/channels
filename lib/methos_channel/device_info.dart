import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: DeviceInfoScreen(),
    );
  }
}

class DeviceInfoScreen extends StatefulWidget {
  @override
  _DeviceInfoScreenState createState() => _DeviceInfoScreenState();
}

class _DeviceInfoScreenState extends State<DeviceInfoScreen> {
  static const platform = MethodChannel('channels/device_info');
  String deviceInfo = 'Unknown';

  Future<void> getDeviceInfo() async {
    try {
      final Map<dynamic, dynamic> result = await platform.invokeMethod('getDeviceInfo');
      setState(() {

        deviceInfo = "MODEL: ${result['MODEL']},'\n"
            "MANUFACTURER: ${result['MANUFACTURER']},\n"
            "VERSION: ${result['VERSION']},\n"
            "SDK_INT: ${result['SDK_INT']},\n"
            "DEVICE: ${result['DEVICE']},\n"
            "ID: ${result['ID']},\n"
            "BOARD: ${result['BOARD']},\n"
            "DISPLAY: ${result['DISPLAY']},\n"
            "HARDWARE: ${result['HARDWARE']}";
      });
    } on PlatformException catch (e) {
      setState(() {
        deviceInfo = "Failed to get device info: '${e.message}'.";
      });
    }
  }

  @override
  void initState() {
    super.initState();
    getDeviceInfo();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("Device Info")),
      body: Center(child: Text(deviceInfo)),
    );
  }
}

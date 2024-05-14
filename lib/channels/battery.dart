import 'package:channel_test1/core/exception_handler.dart';
import 'package:channel_test1/core/toast.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';


class Battery extends StatefulWidget {
  const Battery({super.key});

  @override
  State<Battery> createState() => _BatteryState();
}

class _BatteryState extends State<Battery> {
  final platformChannel = const MethodChannel("asnprz/battery");

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Platform Channels'),
        centerTitle: true,
      ),
      body: SafeArea(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            ElevatedButton(
              onPressed: () => greeting(context),
              child: const Text('Say hello to platform'),
            ),
            const SizedBox(height: 30, width: double.maxFinite),
            ElevatedButton(
              onPressed: () => getBatteryLevel(context),
              child: const Text('Show Battery Level'),
            ),
          ],
        ),
      ),
    );
  }

  greeting(BuildContext context) async {
    ExceptionHandler.handle(() async {
      final argument = {"name": "Ashkan"};
      final String response = await platformChannel.invokeMethod('greeting', argument);
      if (context.mounted) {
        MessageQueue().addMessage(
          message: response,
        );
      }
    });
  }

  getBatteryLevel(BuildContext context) async {
    ExceptionHandler.handle(() async {
      final int batteryLevel = await platformChannel.invokeMethod('getBatteryLevel');
      if (context.mounted) {
        showDialog(
          context: context,
          builder: (context) {
            return AlertDialog.adaptive(
              title: const Text('Battery Level'),
              content: Row(
                children: [
                  const Icon(Icons.battery_full, color: Colors.green),
                  const SizedBox(width: 10),
                  Text('$batteryLevel%'),
                ],
              ),
              actions: [
                TextButton(
                  onPressed: () => Navigator.pop(context),
                  child: const Text('Close'),
                ),
              ],
            );
          },
        );
      }
    });
  }
}
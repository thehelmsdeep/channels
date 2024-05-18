import 'package:flutter/material.dart';
import 'package:flutter/services.dart';



class VolumeControlScreen extends StatefulWidget {
  @override
  _VolumeControlScreenState createState() => _VolumeControlScreenState();
}

class _VolumeControlScreenState extends State<VolumeControlScreen> {
  static const platform = MethodChannel('volume_control_channel');
  int _maxVolume = 0;
  int _currentVolume = 0;

  @override
  void initState() {
    super.initState();
    _initVolume();
  }

  Future<void> _initVolume() async {
    try {
      final int maxVolume = await platform.invokeMethod('getMaxVolume');
      final int currentVolume = await platform.invokeMethod('getCurrentVolume');
      setState(() {
        _maxVolume = maxVolume;
        _currentVolume = currentVolume;
      });
    } on PlatformException catch (e) {
      print("Failed to get volume: '${e.message}'.");
    }
  }

  Future<void> _setVolume(int volumeLevel) async {
    try {
      await platform.invokeMethod('setVolume', volumeLevel);
      setState(() {
        _currentVolume = volumeLevel;
      });
    } on PlatformException catch (e) {
      print("Failed to set volume: '${e.message}'.");
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Volume Control'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text('Max Volume: $_maxVolume'),
            SizedBox(height: 20),
            Text('Current Volume: $_currentVolume'),
            SizedBox(height: 20),
            ElevatedButton(
              onPressed: () {
                if (_currentVolume > 0) {
                  _setVolume(_currentVolume - 1);
                }
              },
              child: Text('Decrease Volume'),
            ),
            SizedBox(height: 20),
            ElevatedButton(
              onPressed: () {
                if (_currentVolume < _maxVolume) {
                  _setVolume(_currentVolume + 1);
                }
              },
              child: Text('Increase Volume'),
            ),
          ],
        ),
      ),
    );
  }
}

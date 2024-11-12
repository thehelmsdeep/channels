import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main()=>runApp(MaterialApp(home: VolumeControlScreen()));



class VolumeControlScreen extends StatefulWidget {
  @override
  _VolumeControlScreenState createState() => _VolumeControlScreenState();
}

class _VolumeControlScreenState extends State<VolumeControlScreen> {

  static const MethodChannel _volumeMethodChannel = MethodChannel('channels/volume_control_channel');
  static const EventChannel _volumeEventChannel = EventChannel('channels/volume_event_channel');
  int _currentVolume = 0;
  int _maxVolume = 0;

  @override
  void initState() {
    super.initState();

    _initializeVolume();

    _volumeEventChannel.receiveBroadcastStream().listen((volume) {
      setState(() {
        _currentVolume = volume;
      });
    });
  }

  Future<void> _initializeVolume() async {
    try {
      final int maxVolume = await _volumeMethodChannel.invokeMethod('getMaxVolume');
      final int currentVolume = await _volumeMethodChannel.invokeMethod('getCurrentVolume');

      setState(() {
        _maxVolume = maxVolume;
        _currentVolume = currentVolume;
      });
    } on PlatformException catch (e) {
      print("Failed to get volume: '${e.message}'.");
    }
  }

  Future<void> _setVolume(int volume) async {
    if (volume < 0 || volume > _maxVolume) return;
    try {
      await _volumeMethodChannel.invokeMethod('setVolume', volume);
    } on PlatformException catch (e) {
      print("Failed to set volume: '${e.message}'.");
    }
  }

  void _increaseVolume() {
    if (_currentVolume < _maxVolume) {
      _setVolume(_currentVolume + 1);
    }
  }

  void _decreaseVolume() {
    if (_currentVolume > 0) {
      _setVolume(_currentVolume - 1);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("Volume Control")),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text('Current Volume: $_currentVolume / $_maxVolume'),
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                IconButton(
                  icon: Icon(Icons.remove),
                  onPressed: _decreaseVolume,
                ),
                IconButton(
                  icon: Icon(Icons.add),
                  onPressed: _increaseVolume,
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }
}

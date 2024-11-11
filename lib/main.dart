
import 'package:channel_test1/channels/volume.dart';
import 'package:flutter/material.dart';
import 'package:oktoast/oktoast.dart';

void main() {
  runApp(const App());
}

class App extends StatelessWidget {
  const App({super.key});

  @override
  Widget build(BuildContext context) {
    return   OKToast(
        textPadding: EdgeInsets.all(8),
        backgroundColor: Colors.black54,
        animationCurve: Curves.easeIn,
        animationDuration: Duration(milliseconds: 400),
        position: ToastPosition.bottom,
        duration: Duration(seconds: 3),
        child: MaterialApp(home: VolumeControlScreen()));
  }
}



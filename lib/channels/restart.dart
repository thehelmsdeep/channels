
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class Restart extends StatefulWidget {
  const Restart({super.key});

  @override
  State<Restart> createState() => _RestartState();
}

class _RestartState extends State<Restart> {

  int count = 0 ;

  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addPostFrameCallback((timeStamp) {
      count=1;
      setState(() {});
    });
  }



  final channel = const MethodChannel('asnprz/restart');

  Future<void> appRestart() async {
    if (kIsWeb) {
      //window.location.replace(window.origin.toString());
      return;
    }
    await channel.invokeMethod('restartApp');
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text('$count'),
            OutlinedButton( onPressed: (){
              count++;
              setState(() {});
            }, child: const Text('increment')),
            OutlinedButton( onPressed: appRestart, child: const Text('Restart')),
          ],
        ),
      ),
    );
  }
}

import 'package:flutter/services.dart';

class ContactService {
  static const MethodChannel _platform =
  MethodChannel('channels/contacts');

  Future<List<Map<String, String>>> getContacts() async {
    try {
      final List<dynamic> result = await _platform.invokeMethod('getContacts');
      return result.map((e) => Map<String, String>.from(e)).toList();
    } on PlatformException catch (e) {
      print("Failed to get contacts: '${e.message}'.");
      return [];
    }
  }
}

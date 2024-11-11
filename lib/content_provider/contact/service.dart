import 'package:flutter/services.dart';

class ContactService {
  static const MethodChannel _platform =
  MethodChannel('com.example.your_project/contacts');

  // Fetch contacts from Android native code
  Future<List<Map<String, String>>> getContacts() async {
    try {
      // Fetch the contacts using the method channel
      final List<dynamic> result = await _platform.invokeMethod('getContacts');
      // Return the result as a list of maps
      return result.map((e) => Map<String, String>.from(e)).toList();
    } on PlatformException catch (e) {
      print("Failed to get contacts: '${e.message}'.");
      return [];
    }
  }
}

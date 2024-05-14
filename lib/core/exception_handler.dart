import 'dart:io';
import 'package:flutter/services.dart';
import 'package:channel_test1/core/toast.dart';


// Define a class for handling exceptions and displaying messages
class ExceptionHandler {
  // Define a method to handle exceptions
  static Future<void> handle(Function callback) async {
    try {
      // Execute the provided callback function
      await callback();
    } on PlatformException catch (error) {
      // Handle platform-specific exceptions
      _showErrorMessage("Platform Exception: ${error.message}");
    } on SocketException catch (error) {
      // Handle network-related exceptions
      _showErrorMessage("Network Exception: ${error.message}");
    } on HttpException catch (error) {
      // Handle HTTP exceptions
      _showErrorMessage("HTTP Exception: ${error.message}");
    } on FormatException catch (error) {
      // Handle format-related exceptions
      _showErrorMessage("Format Exception: ${error.message}");
    } catch (error) {
      // Handle generic exceptions
      _showErrorMessage("Error: ${error.toString()}");
    }
  }

  // Define a private method to display error messages
  static void _showErrorMessage(String message) {
    // Display error message using toast
    MessageQueue().addMessage(message: message);
  }
}

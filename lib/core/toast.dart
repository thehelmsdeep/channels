
import 'dart:ui';

import 'package:oktoast/oktoast.dart';


class MessageQueue {
  static final MessageQueue _instance = MessageQueue._internal();

  factory MessageQueue() {
    return _instance;
  }

  MessageQueue._internal();

  final _messageQueue = <String>[];
  bool _isQueueProcessing = false;

  void addMessage({required String message}) {
    _messageQueue.add(message);
    if (!_isQueueProcessing) {
      _processQueue();
    }
  }

  void _processQueue() async {
    _isQueueProcessing = true;
    while (_messageQueue.isNotEmpty) {
      final message = _messageQueue.removeAt(0);
      await _showMessage(message);
    }
    _isQueueProcessing = false;
  }

  Future<void> _showMessage(String message) async {
    showToast(
      message,
      textDirection: TextDirection.rtl,
      duration: const Duration(seconds: 2),
    );

    await Future.delayed(const Duration(seconds: 2));
  }
}

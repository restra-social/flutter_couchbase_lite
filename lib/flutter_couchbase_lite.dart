import 'dart:async';

import 'package:flutter/services.dart';

class FlutterCouchbaseLite {
  static const MethodChannel _channel =
      const MethodChannel('flutter_couchbase_lite');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  Future<Null> startReplicator(String url, String username, String password) async {
    try {
      await _channel.invokeMethod('startReplicator', <String , dynamic> {
        'url' : url,
        'username': username,
        'password': password
      });
    } on PlatformException catch(e) {
      throw 'Unable to Start Replicator ${e.message}';
    }
  }
}

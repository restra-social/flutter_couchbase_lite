import 'dart:async';
import 'package:flutter/services.dart';

abstract class FlutterCouchbaseLite {
  static const MethodChannel _channel =
      const MethodChannel('flutter_couchbase_lite');

  static Future<String> initDatabaseWithName(String _name) async {
    try {
      final String result =
      await _channel.invokeMethod('initDatabaseWithName', <String, String>{
        "name": _name,
      });
      return result;
    } on PlatformException catch (e) {
      throw 'unable to init database $_name: ${e.message}';
    }
  }

  static Future<String> saveDocument(Map<String, dynamic> _map) async {
    try {
      final String result = await _channel.invokeMethod('saveDocument', _map);
      return result;
    } on PlatformException {
      throw 'unable to save the document';
    }
  }

  static Future<Null> startReplicator(String _url, String _replicationType, Map<String,String> _credentials) async {
    try {
      final String result = await _channel.invokeMethod('startReplicator', <String , dynamic> {
        'url' : _url,
        'type': _replicationType,
        'credentials': _credentials
      });
      return result;
    } on PlatformException catch(e) {
      throw 'Unable to Start Replicator ${e.message}';
    }
  }
}

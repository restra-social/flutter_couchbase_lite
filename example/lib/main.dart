import 'package:flutter/material.dart';
import 'dart:async';
import 'package:flutter/services.dart';
import 'package:flutter_couchbase_lite/flutter_couchbase_lite.dart';

void main() => runApp(new MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => new _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _message = 'Unknown !';

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String id;

    try{
//      String _cbInstance = await FlutterCouchbaseLite.startReplicator("ws://localhost:4984/todolite", "PUSH_AND_PULL", <String, String>{
//        'username': 'Administrator',
//        'password': '123456789'
//      });
//      print(_cbInstance);
//      _message = _cbInstance;
    String msg = await FlutterCouchbaseLite.initDatabaseWithName("todolite");
    print(msg);

    id = await FlutterCouchbaseLite.saveDocument(<String, Object>{
        "id": 123,
        "type": "SomeType"
    });

    }catch (e){
      print(e.toString());
    }

    setState(() {
      _message = id;
    });
  }

  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      home: new Scaffold(
        appBar: new AppBar(
          title: const Text('Plugin example app'),
        ),
        body: new Center(
          child: new Text('Doc Id: $_message\n'),
        ),
      ),
    );
  }
}

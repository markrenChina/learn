import 'package:flutter/material.dart';

//import 'dart:ffi';
//import 'dart:io';
import 'package:system_clock/system_clock.dart';
import 'package:web_socket_channel/io.dart';
import 'package:web_socket_channel/status.dart' as status;

void test() async{


  var channel = IOWebSocketChannel.connect(Uri.parse('ws://192.168.1.171:19201/websocket/eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJsb2dpblR5cGUiOiJsb2dpbiIsImxvZ2luSWQiOjMsInJuU3RyIjoiSlkyM2trdXBaaThwT0hRcnpSTGdmM2k4bFpHaWlpbnMiLCJ1aWQiOjMsInVzZXJuYW1lIjoiZGliYW5nIn0.vDwne95re0gZiZLXE2bIHZil4b36yQOlatxNDqnSBCg'));

  channel.stream.listen((message) {
    print(message);
    channel.sink.add('received!');
    channel.sink.close(status.goingAway);
  });

}

void main() {
  runApp(const MyApp());
  test();
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});


  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _counter = 0;

  void _incrementCounter() {
    setState(() {
      _counter =  SystemClock.elapsedRealtime().inMilliseconds;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            const Text(
              'You have pushed the button this many times:',
            ),
            Text(
              '$_counter',
              style: Theme.of(context).textTheme.headline4,
            ),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _incrementCounter,
        tooltip: 'Increment',
        child: const Icon(Icons.add),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}

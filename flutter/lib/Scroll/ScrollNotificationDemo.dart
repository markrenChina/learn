import 'package:flutter/material.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter demo',
      initialRoute: "/",
      theme: ThemeData(
        primarySwatch: Colors.blue,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      routes: {"/": (context) => ScrollNotificationTest()},
    );
  }
}

class ScrollNotificationTest extends StatefulWidget {
  ScrollNotificationTest({Key key}) : super(key: key);

  @override
  _ScrollNotificationTestState createState() => _ScrollNotificationTestState();
}

class _ScrollNotificationTestState extends State<ScrollNotificationTest> {
  String _progress = "0%"; //保存进度条百分比

  //构建UI
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
          child: Scrollbar(
        //进度条
        //监听滚动通知
        child: NotificationListener<ScrollNotification>(
          onNotification: (ScrollNotification notification) {
            double progress = notification.metrics.pixels /
                notification.metrics.maxScrollExtent;
            //重新构建
            setState(() {
              _progress = "${(progress * 100).toInt()}%";
            });
            print("BottomEdge: ${notification.metrics.extentAfter == 0}");
            return true; //放开此行注释后，进度条将失效
          },

          child: Stack(
            alignment: Alignment(0.9,-0.87),
            children: [
              ListView.builder(
                itemBuilder: (context,index){
                  return ListTile(title: Text("$index"),);
                },
                itemCount: 100,
                itemExtent: 50.0,
              ),
              CircleAvatar(//显示进度百分比
                radius: 30.0,
                child: Text(_progress),
                backgroundColor: Colors.black54,
              )
            ],
          ),
        ),
      )), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}

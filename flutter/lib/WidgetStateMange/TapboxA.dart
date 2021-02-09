import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

/// widget 管理自身状态
void main() {

  runApp(MyApp());

  //路由传值
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter demo',
      initialRoute: "/",
      theme: ThemeData(
        //应用主题
        primarySwatch: Colors.blue,

        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      //注册路由表
      routes: {"/": (context) => TapboxA()},
    );
  }
}

class TapboxA extends StatefulWidget{

  TapboxA({Key key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _TapbaxAState();
  }}

class _TapbaxAState extends State<TapboxA>{
  bool _active = false;

  void _handleTap(){
    setState(() {
      _active = !_active;
    });
  }

  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: _handleTap,
      child: Container(
        child: Center(
          child: new Text(
            _active ? 'Active' : 'Inactive',
            style: TextStyle(fontSize: 32.0, color: Colors.white),
          ),
        ),
        width: 200.0,
        height: 200.0,
        decoration: BoxDecoration(
          color: _active ? Colors.lightGreen[700] : Colors.grey[600],
        ),
      ),
    );
  }

}

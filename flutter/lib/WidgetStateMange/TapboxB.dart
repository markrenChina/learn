import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

/// 父widget管理子Widget状态
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
      routes: {"/": (context) => ParentWidget()},
    );
  }
}

class TapboxB extends StatelessWidget{

  TapboxB({Key key, this.active: false, @required this.onChanged}) : super(key: key);

  final bool active;
  final ValueChanged<bool> onChanged;

  //传值给父_handleTapboxChanged方法 父方法setState改变UI
  void _handleTap(){
    onChanged(!active);
  }

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: _handleTap,
      child: Container(
        child: Center(
          child: Text(
            active ? 'Active' : 'Inactive',
            style: TextStyle(fontSize: 32.0,color: Colors.white),
          ),
        ),
        width: 200.0,
        height: 200.0,
        decoration: BoxDecoration(
          color: active ? Colors.lightGreen[700] : Colors.grey[600]
        ),
      ),
    );
  }
}

class ParentWidget extends StatefulWidget{
  @override
  State<StatefulWidget> createState() => _ParentWidgetState();
}

class _ParentWidgetState extends State<ParentWidget>{
  bool _active = false;
  void _handleTapboxChanged(bool newValue){
    setState(() {
      _active = newValue;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      child: TapboxB(
        active: _active,
        onChanged: _handleTapboxChanged,
      ),
    );
  }

}


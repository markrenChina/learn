import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

/// Widget状态混合管理
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
      routes: {"/": (context) => ParentWidget()},
    );
  }
}

//这里跟A一样要改为有状态的StatefulState
class TapboxC extends StatefulWidget{

  TapboxC({Key key, this.active: false, @required this.onChanged}) : super(key: key);

  final bool active;
  final ValueChanged<bool> onChanged;

  @override
  _TapboxCState createState() => _TapboxCState();

}

class _TapboxCState extends State<TapboxC>{
  bool _highlight = true;

  void _handleTapDown(TapDownDetails details){
    setState(() {
      _highlight = true;
    });
  }

  void _handleTapUp(TapUpDetails details){
    setState(() {
      _highlight = false;
    });
  }

  void _handleTapCancel(){
    setState(() {
      _highlight = false;
    });
  }

  void _handleTap(){
    widget.onChanged(!widget.active);
  }

  @override
  Widget build(BuildContext context) {
    //在按下时添加绿色边框，抬起时，取消高亮
    return GestureDetector(
      onTapDown: _handleTapDown,
      onTapUp: _handleTapUp,
      onTapCancel: _handleTapCancel,
      onTap: _handleTap,
      child: Container(
        child: Center(
          child: Text(
            widget.active ? 'Active' : 'Inactive',
            style: TextStyle(fontSize: 32.0,color: Colors.white),
          ),
        ),
        width: 200.0,
        height: 200.0,
        decoration: BoxDecoration(
            color: widget.active ? Colors.lightGreen[700] : Colors.grey[600],
            border: _highlight ? Border.all(color: Colors.teal[700], width:10.0,) : null
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
      child: TapboxC(
        active: _active,
        onChanged: _handleTapboxChanged,
      ),
    );
  }

}


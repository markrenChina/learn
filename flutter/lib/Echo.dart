import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class Echo extends StatelessWidget{

  const Echo({Key key,
    @required this.text,
    this.backgroundColor: Colors.grey,
  }): super(key: key);

  final String text;
  final Color backgroundColor;

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Container(
        color: backgroundColor,
        child: Text(text),
      ),
    );
  }
}
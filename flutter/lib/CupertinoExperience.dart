import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

void main() => runApp(new CupertinoApp(
  home: new CupertinoPageScaffold(
    navigationBar: CupertinoNavigationBar(
      middle: Text("Cupertino Demo"),
    ),
    child: Center(
      child: CupertinoButton(
        color: CupertinoColors.activeBlue,
        child: Text("press"),
        onPressed: (){},
      )
    ),
  ),
));
import 'package:flutter/cupertino.dart';

//AssetImage 返回的是一个ImageProvider
//加载依赖时必须使用这种方法，AssetImage('graphics/background.png', package: 'imageName')
Widget build(BuildContext context){
  return DecoratedBox(
    decoration: BoxDecoration(
      image: DecorationImage(
        image: AssetImage('graphics/background.png')
      )
    ),
  );
}

//Image.asset()返回一个Widget
Widget build2(BuildContext context){
  return Image.asset('graphics/background.png');
}
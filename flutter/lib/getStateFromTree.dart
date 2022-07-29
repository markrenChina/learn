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
        //应用主题
        primarySwatch: Colors.blue,

        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      //注册路由表
      routes: {"/": (context) => MyHomePage()},
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key}) : super(key: key);

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  //构建UI
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("子树中获取State对象"),
      ),
      body: Center(
        child: Builder(
          builder: (context) {
            return RaisedButton(onPressed: () {
              //查找父级最近与Scaffold对应的ScaffoldState对象
              //ScaffoldState _state = context.ancestorStateOfType(TypeMatcher<ScaffoldState>());

              //ancestorStateOfType 已过时，改用findAncestorStateOfType 但是不推荐在build中使用
              //ScaffoldState _state = context.findAncestorStateOfType();

              //通过StatefulWidget 提供的of静态方法获取
              ScaffoldState _state = Scaffold.of(context);
              //调用ScaffoldState的showSnackBar来弹出SnackBar
              _state.showSnackBar(SnackBar(
                content: Text("我是 SnackBar"),
              ));
            });
          },
        ),
      ),
    );
  }
}

//Scaffold.of 源码中提供的示例方法1 此build包裹在Scaffold下
Widget build1(BuildContext context) {
  return Center(
    child: ElevatedButton(
      child: Text('SHOW A SNACKBAR'),
      onPressed: () {
        Scaffold.of(context).showSnackBar(
          SnackBar(
            content: Text('Have a snack!'),
          ),
        );
      },
    ),
  );
}

//Scaffold.of 源码中提供的示例方法2
//中间加一层builder: (BuildContext context)
Widget build2(BuildContext context) {
  return Scaffold(
    appBar: AppBar(title: Text('Demo')),
    body: Builder(
      // Create an inner BuildContext so that the onPressed methods
      // can refer to the Scaffold with Scaffold.of().
      builder: (BuildContext context) {
        return Center(
          child: ElevatedButton(
            child: Text('SHOW A SNACKBAR'),
            onPressed: () {
              Scaffold.of(context).showSnackBar(SnackBar(
                content: Text('Have a snack!'),
              ));
            },
          ),
        );
      },
    ),
  );
}

//GlobalKey方式
//static GlobalKey<ScaffoldState> _globalKey = GlobalKey()
/**
 * ...
 * Scaffold(
 *  key: _globalKey
 * )
 */
// _globalKey.currentState.openDrawer
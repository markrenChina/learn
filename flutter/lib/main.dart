import 'package:first_flutter_app/Echo.dart';
import 'package:first_flutter_app/RandomWordsWidget.dart';
import 'package:flutter/material.dart';

void main() {

  runApp(MyApp());

  //路由传值
}

//StatelessWidget 无状态的Widget
class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  //myApp本质上是一个widget
  //widget主要工作是提供一个build（）方法来描述如何构建UI
  @override
  Widget build(BuildContext context) {
    //MaterialApp 是Material库中提供Flutter APP框架，本质也是一个Widget
    return MaterialApp(
      title: 'Flutter demo',
      //将/作为路由首页，从这里可以看出Flutter的设计是基于web的形式
      initialRoute: "/",
      theme: ThemeData(
        // This is the theme of your application.
        //应用主题
        primarySwatch: Colors.blue,
        // This makes the visual density adapt to the platform that you run
        // the app on. For desktop platforms, the controls will be smaller and
        // closer together (more dense) than on mobile platforms.
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      //注册路由表
      routes: {
        //"new_page":(context)=>NewRoute(),
        //命名路由传值
        "new_page":(context)=>EchoRoute(),
        "/":(context)=> MyHomePage(title: 'Flutter Demo Home Page',)
      },
      //onGenerateRoute 只对非注册的命名路由生效
      /*onGenerateRoute: (RouteSettings settings){
        return MaterialPageRoute(builder: (context){
          String routeName = settings.name;
        });
      },*/
      //应用首页路由  ？？相当于android 中的activity配置？？
      //home: MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

//首页 StatefulWidget 有状态的widget
class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  final String title;

  //build方法放在了_MyHomePageState
  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  //计数器
  int _counter = 0;

  //自增函数
  void _incrementCounter() {
    //后setState （通知flutter 更新需要更新的widget）
    setState(() {
      //先自增
      _counter++;
    });
  }

  //构建UI
  @override
  Widget build(BuildContext context) {
    // This method is rerun every time setState is called, for instance as done
    // by the _incrementCounter method above.
    //
    // The Flutter framework has been optimized to make rerunning build methods
    // fast, so that you can just rebuild anything that needs updating rather
    // than having to individually change instances of widgets.
    //Scaffold 是 Material库中提供的页面脚手架 body含有组件树
    return Scaffold(
      appBar: AppBar(
        // Here we take the value from the MyHomePage object that was created by
        // the App.build method, and use it to set our appbar title.
        title: Text(widget.title),
      ),
      body: Center(
        //组件树
        // Center is a layout widget. It takes a single child and positions it
        // in the middle of the parent.
        //列下2个text
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(
              '!!!!!You have pushed the button this many times:',
            ),
            Text(
              '$_counter',
              style: Theme.of(context).textTheme.headline4,
            ),
            FlatButton(
              child: Text("open new route"),
              textColor: Colors.blue,
              onPressed: () {
                //导航到新路由 MaterialPageRoute 是Material库提供的组件 提供了不同平台的一致切换动画
                /**
                 * MaterialPageRoute({
                    @required this.builder,
                    RouteSettings settings,
                    this.maintainState = true,
                    bool fullscreenDialog = false,
                    })

                    builder是一个WidgetBuilder类型的回调函数 需要返回新路由的实例
                    settings 配置信息
                    maintainState 老页面是否保留在内存
                    fullscreenDialog 是否为全屏的模拟对话框
                 */
                //Navigator 通过栈路由管理组件
                /**
                 * 入栈
                 * Navigator.push(BuildContext context, Route route);
                 * 源码中举例：
                 * Navigator.push(context, MaterialPageRoute(builder: (BuildContext context) => MyPage()));
                 * 出栈
                 * bool pop(BuildContext context, [result])
                 * 源码中举例：
                 * Navigator.pop(context, true);
                 */
                /*Navigator.push(context, MaterialPageRoute(builder: (context) {
                  //return NewRoute();
                  //非命名路由传值
                  return RouterTestRoute();
                }));*/
                //Navigator.pushNamed(context, "new_page");
                //命名路由传值
                Navigator.of(context).pushNamed("new_page",arguments: "hi");
              },
            ),
            RandomWordsWidget(),
            Echo(text: "hello world")
          ],
        ),
      ),
      //右下角的+
      floatingActionButton: FloatingActionButton(
        //_incrementCounter 作为onPressed 的回调
        onPressed: _incrementCounter,
        tooltip: 'Increment',
        child: Icon(Icons.add),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}

class NewRoute extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("New Route"),
      ),
      body: Center(
        child: Text("This is new route"),
      ),
    );
  }
}
//非命名路由传值
class TipRoute extends StatelessWidget {
  TipRoute({Key key, @required this.text}) : super(key: key);
  final String text;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text("提示"),
        ),
        body: Padding(
          padding: EdgeInsets.all(18),
          child: Center(
            child: Column(
              children: [
                Text(text),
                RaisedButton(onPressed: () => Navigator.pop(context, "我是返回值"))
              ],
            ),
          ),
        ));
  }
}
//非命名路由传值
class RouterTestRoute extends StatelessWidget{
  @override
  Widget build(BuildContext context) {
    return Center(
      child: RaisedButton(
        onPressed: ()async{
          //打开TipRoute，并等待返回结果
          var result = await Navigator.push(
            context,
            MaterialPageRoute(
                builder: (context){
                  return TipRoute(text: "我是提示XXX");
                },),
          );
          //输出TipRoute路由返回的结果
          print("路由的返回结果 $result");
        },
        child: Text("打开提示页"),
      ),
    );

  }

}
//命名路由传值
class EchoRoute extends StatelessWidget{
  @override
  Widget build(BuildContext context) {
    //获取路由参数
    var args=ModalRoute.of(context).settings.arguments;
    print('args = $args');

    return Scaffold(
      appBar: AppBar(
        title: Text("New Route"),
      ),
      body: Center(
        child: Text("This is new route"),
      ),
    );
  }

}

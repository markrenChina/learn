//GridView 特有SliverGriDelegate属性
//flutter内置2个SliverGriDelegate的实现类：
//SliverGridDelegateWithFixedCrossAxisCount 横轴固定数量 简化用GridView.count
//SliverGridDelegateWithMaxCrossAxisExtent 纵轴固定数量 简化用GridView.extent

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
      routes: {"/": (context) => MyHomePage(title: 'Flutter Demo Home Page',)},
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {

  //构建UI
  @override
  Widget build(BuildContext context) {

    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: InfiniteGridView()
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}

class InfiniteGridView extends StatefulWidget{
  @override
  State<StatefulWidget> createState() => _InfiniteGridViewState();
}

class _InfiniteGridViewState extends State<InfiniteGridView>{

  List<IconData> _icons = [];//保持Icon数据

  @override
  void initState() {
    super.initState();
    _retrieveIcons();
  }
  @override
  Widget build(BuildContext context) {
    return GridView.builder(
        gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
          crossAxisCount: 3 ,//每行三列
          childAspectRatio: 1.0 //显示区域宽高相等
        ),
        itemCount: _icons.length,
        itemBuilder: (context,index){
          //如果显示到最后一个并且Icon的总数小于200，则继续读取数据
          if(index == _icons.length - 1 && _icons.length < 200){
            _retrieveIcons();
          }
          return Icon(_icons[index]);
        }
    );
  }

  void _retrieveIcons(){
    Future.delayed(Duration(milliseconds: 200)).then((value) {
      setState(() {
        _icons.addAll([
          Icons.ac_unit,
          Icons.airport_shuttle,
          Icons.all_inclusive,
          Icons.beach_access,
          Icons.cake,
          Icons.free_breakfast
        ]);
      });
    });
  }
}
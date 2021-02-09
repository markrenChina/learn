import 'package:english_words/english_words.dart';
import 'package:flutter/material.dart';


//listView 使用数组作为其children时是非sliver懒加载模型
//ListView.builder时时作为sliver模型
//ListView.separated 比ListView.builder多了一个separatorBuilder参数，（组件分割器）
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
        child: InfiniteListView()
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }

}

class InfiniteListView extends StatefulWidget{
  @override
  State<StatefulWidget> createState() => _InfiniteListViewState();
}

class _InfiniteListViewState extends State<InfiniteListView>{
  static const loadingTag = '##loading##';//表尾标记
  var _words = <String>[loadingTag];

  @override
  void initState() {
    super.initState();
    _retrieveData();
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        ListTile(title: Text("我是表头")),
        Expanded(
            child: ListView.separated(
                itemBuilder: (context,index) {
                  //如果到了表尾
                  if(_words[index] == loadingTag){
                    //不足100条就继续获取数据
                    if(_words.length -1 < 100){
                      //获取数据
                      _retrieveData();
                      //加载时显示loading
                      return Container(
                        padding: const EdgeInsets.all(16.0),
                        alignment: Alignment.center,
                        child: SizedBox(
                          width: 24.0,
                          height: 24.0,
                          child: CircularProgressIndicator(strokeWidth: 2.0,),
                        ),
                      );
                    }else{
                      //已经加载了100条不再获取数据
                      return Container(
                        alignment: Alignment.center,
                        padding: EdgeInsets.all(16.0),
                        child: Text("没有更多了",style: TextStyle(color: Colors.grey),),
                      );
                    }
                  }//显示单词列表项
                  return ListTile(title: Text(_words[index]));
                },
                separatorBuilder: (context,index) => Divider(height: .0,),
                itemCount: _words.length)
            )
      ],
    );

  }

  void _retrieveData(){
    Future.delayed(Duration(seconds: 2)).then((value) {
      _words.insertAll(_words.length-1,
          //每次生成20个单词
          generateWordPairs().take(20).map((e) => e.asPascalCase).toList()

      );
      setState(() {
        //重构列表
      });
    });
    }
}


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
      routes: {"/": (context) => CustomScrollViewTestRoute()},
    );
  }
}

class CustomScrollViewTestRoute extends StatelessWidget{
  @override
  Widget build(BuildContext context) {
    return CustomScrollView(
      slivers: [
        //appBar,导航
        SliverAppBar(
          pinned: true,
          expandedHeight: 250,
          flexibleSpace: FlexibleSpaceBar(
            title: const Text('Demo'),
            background: Image.asset(
              "images/avatar.jpeg",fit: BoxFit.cover,
            ),
          ),
        ),
        SliverPadding(
          padding: const EdgeInsets.all(8.0),
          sliver: SliverGrid(//Grid
            gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
              crossAxisCount: 2,
              mainAxisSpacing: 10.0,
              crossAxisSpacing: 10.0,
              childAspectRatio: 4.0
            ),
            delegate: SliverChildBuilderDelegate(
                (BuildContext context,int index){
                  //创建子widget
                  return Container(
                    alignment: Alignment.center,
                    color: Colors.cyan[100*(index%9)],
                    child: Text('grid $index'),
                  );
                },
              childCount: 20,
            ),
          ),
        ),
        //List
        SliverFixedExtentList(
            delegate: SliverChildBuilderDelegate(
                (BuildContext context,int index) {
                  //创建列表项
                  return Container(
                    alignment: Alignment.center,
                    color: Colors.lightBlue[100*(index%9)],
                    child: Text('list item $index'),
                  );
                },
              childCount: 50
            ), itemExtent: 50.0,
        )
      ],
    );
  }

}
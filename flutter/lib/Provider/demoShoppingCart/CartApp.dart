import 'package:first_flutter_app/Provider/commontool/ChangeNotifierProvider.dart';
import 'package:first_flutter_app/Provider/commontool/Cousumer.dart';
import 'package:first_flutter_app/Provider/demoShoppingCart/CartModel.dart';
import 'package:flutter/material.dart';

import 'GoodsInfo.dart';

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
      routes: {"/": (context) => MyHomePage(title: '跨组件状态管理',)},
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
        child: ProviderRoute()
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}

class ProviderRoute extends StatefulWidget {
  ProviderRoute({Key key}) : super(key: key);

  @override
  _ProviderRouteState createState() => _ProviderRouteState();
}

class _ProviderRouteState extends State<ProviderRoute> {
  //构建UI
  @override
  Widget build(BuildContext context) {
    return Center(
      child: ChangeNotifierProvider<CartModel>(
        data: CartModel(),
        child: Builder(
          builder: (context) {
            return Column(
              children: [
                /*Builder(builder: (context) {
                  var cart = ChangeNotifierProvider.of<CartModel>(context);
                  return Text("总价： ${cart.totalPrice}");
                }),*/
                Consumer<CartModel>(builder: (context, cart)=> Text("总价： ${cart.totalPrice}")),
                Builder(builder: (context) {
                  print('RaisedButton build');
                  return RaisedButton(
                      child: Text("添加商品"),
                      onPressed: (){
                        //向购物车中添加商品，添加后总价会更新
                        ChangeNotifierProvider.of<CartModel>(context,listen: false).add(Item(20.0,1));
                      });
                }),
              ],
            );
          },
        ),
      ),
    );
  }
}

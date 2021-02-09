import 'dart:collection';
import 'package:flutter/material.dart';

import 'GoodsInfo.dart';

class CartModel extends ChangeNotifier{

  //用于保存购物车中的商品列表
  final List<Item> _items = [];

  //禁止改变购物车里的商品信息
  UnmodifiableListView<Item> get items => UnmodifiableListView(_items);

  //购物车中的商品总价
  double get totalPrice => _items.fold(0, (previousValue, element) =>
  previousValue + element.count * element.price);

  //往_items中添加，这是唯一一种能从外表改变购物车的方法
  void add(Item item){
    print('添加商品');
    _items.add(item);
    //通知监听器（订阅者），重新构建InheritedProvider，更新状态
    notifyListeners();
  }
}
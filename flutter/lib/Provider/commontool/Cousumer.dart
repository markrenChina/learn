import 'package:first_flutter_app/Provider/commontool/ChangeNotifierProvider.dart';
import 'package:flutter/cupertino.dart';

class Consumer<T> extends StatelessWidget {
  Consumer({Key key, @required this.builder, this.child})
      : assert(builder != null),
        super(key: key);

  final Widget child;
  final Widget Function(BuildContext context, T value) builder;

  @override
  Widget build(BuildContext context) {
    return builder(
      context,
      ChangeNotifierProvider.of<T>(context) //自动获取model
    );
  }
}


animateColorAsState 变换颜色
- val backgroundColor = if (tabPage == TabPage.Home) Purple100 else Green300
+ val backgroundColor by animateColorAsState(if (tabPage == TabPage.Home) Purple100 else Green300)

scroll 滚动动画
AnimatedVisibility 替换 if

自定义动画
整加 enter 和 exit

Column 展开
Modifier.animateContentSize()




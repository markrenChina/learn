package com.ccand99.complexlayout

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ccand99.complexlayout.ui.theme.ComplexlayoutTheme

@Composable
fun Demo() {
    ComplexlayoutTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            //第一层背景
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawRect(Color.Gray)
            }
            //第二层布局
            LazyColumn {
                //为了凑长度
                item{ Greeting("markrenChina love Android！！！") }
                item{ Greeting("markrenChina love Android！！！") }
                item{ Greeting("markrenChina love Android！！！") }
                item{ Greeting("markrenChina love Android！！！") }
                item{ Greeting("markrenChina love Android！！！") }
                item{ Greeting("markrenChina love Android！！！") }
                item{ Greeting("markrenChina love Android！！！") }
                item{ Greeting("markrenChina love Android！！！") }
                item{ Greeting("markrenChina love Android！！！") }
                item{ Greeting("markrenChina love Android！！！") }
                item{ Greeting("markrenChina love Android！！！") }
                item{ Greeting("markrenChina love Android！！！") }
                item{ Greeting("markrenChina love Android！！！") }
                item{ Greeting("markrenChina love Android！！！") }
                item{ Greeting("markrenChina love Android！！！") }
                item{ Greeting("markrenChina love Android！！！") }
                item{ Greeting("markrenChina love Android！！！") }
            }
            //第三层布局
            LazyRow {
                item{ Greeting("Android") }
                item{ Greeting("Android") }
                item{ Greeting("Android") }
                item{ Greeting("Android") }
                item{ Greeting("Android") }
                item{ Greeting("Android") }
                item{ Greeting("Android") }
                item{ Greeting("Android") }
            }
            //第四层布局 右下角放一个圆圈
            Canvas(modifier = Modifier
                .layout { measurable, constraints ->
                    Log.i("with * height", "${constraints.maxWidth}  * ${constraints.maxHeight}")
                    val p = measurable.measure(constraints)
                    layout(constraints.maxWidth, constraints.maxHeight) {
                        //荣耀20 1dp = 3
                        p.placeRelative(constraints.maxWidth- 300, constraints.maxHeight - (50+80)*3)
                    }
                }
                .size(80.dp)
            ) {
                Log.i("with * height", "${size.width}  * ${size.height}")
                drawCircle(
                    Color.Red//.copy(0.4f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Demo()
}
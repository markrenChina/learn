package com.ccand99.demo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ccand99.demo.animation.AnimationActivity
import com.ccand99.demo.counter.Counter1
import com.ccand99.demo.counter.Counter2
import com.ccand99.demo.list.ImagesList
import com.ccand99.demo.list.NameList
import com.ccand99.demo.list.SamplerList
import com.ccand99.demo.list.ScrollingList
import com.ccand99.demo.state.TodoActivity
import com.ccand99.demo.theming.ThemingActivity
import com.ccand99.demo.ui.theme.AppTheme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(binding.root)

        // Example of a call to a native method
        //binding.sampleText.text = stringFromJNI()

        setContent {
            MyApp(this) {}
        }
    }

}

@Composable
fun MyApp(
    activity: Activity,
    content: @Composable () -> Unit
) {
    AppTheme {
        //ScrollingList()
        //Surface(color = MaterialTheme.colors.background) {
        //    content()
        //}
        Column {
            Button(onClick = { activity.startActivity(Intent(activity,TodoActivity::class.java))}) {
                Text(text = "TODO Activity")
            }
            Box(modifier = Modifier.height(10.dp))
            Button(onClick = { activity.startActivity(Intent(activity,ThemingActivity::class.java))}) {
                Text(text = "Theming Activity")
            }
            Box(modifier = Modifier.height(10.dp))
            Button(onClick = { activity.startActivity(Intent(activity,AnimationActivity::class.java))}) {
                Text(text = "Animation Activity")
            }
        }
    }
}

@Composable
fun MyScreenContent(names: List<String> = List(1000) { "Hello Android $it" }) {
    Column(modifier = Modifier.fillMaxHeight()) {

        /*Column(modifier = Modifier.weight(1f)) {
            for (name in names) {
                Greeting(name = name)
                Divider(color = Color.Black)
            }
        }*/

        //list
        Column(modifier = Modifier.weight(1f)) {
            NameList(names = names)
        }

        Divider(color = Color.Transparent, thickness = 32.dp)
        Counter1()
        Divider(color = Color.Transparent, thickness = 32.dp)
        //父组件管理子组件状态
        val counterStat = remember {
            mutableStateOf(0)
        }
        Counter2(
            count = counterStat.value,
            updateCount = { newCount ->
                counterStat.value = newCount
            }
        )
    }
}

@Composable
fun Greeting(name: String?) {
    //点击改变颜色 引入状态管理
    var isSelected by remember { mutableStateOf(false) }
    val backgroundColor by animateColorAsState(targetValue = if (isSelected) Color.Red else Color.Transparent)
    Text(
        text = "Hello $name!",
        modifier = Modifier
            .padding(24.dp)
            .background(color = backgroundColor)
            .clickable(onClick = { isSelected = !isSelected }),
        //style = MaterialTheme.typography.h1
        style = MaterialTheme.typography.body1.copy(color = Color.Green)
    )
}


@Preview(name = "Text preview", showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp(Activity()) {
        //Greeting(name = "Android")
        MyScreenContent()
    }
}
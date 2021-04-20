package com.ccand99.demo.counter

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

/**
 * 计数器demo
 */
@Composable
fun Counter1() {
    val count = remember {
        mutableStateOf(0)
    }
    Button(onClick = { count.value++ }) {
        Text(text = "I,ve been clicked ${count.value} times")
    }
}

/**
 * since consumers of Counter can be interested in the state,
 * you can defer it to the caller completely by introducing a
 * (count, updateCount) pair as parameters of Counter.
 * In this way, Counter is hoisting its state:
 */
@Composable
fun Counter2(count: Int, updateCount: (Int) -> Unit) {
    Button(onClick = { updateCount(count+1) } , colors = ButtonDefaults.buttonColors(
        backgroundColor = if (count > 5) Color.Green else Color.White
    )) {
        Text("I've been clicked $count times")
    }
}



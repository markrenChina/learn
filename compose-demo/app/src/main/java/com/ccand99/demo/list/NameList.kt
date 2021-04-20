package com.ccand99.demo.list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ccand99.demo.Greeting


@Composable
fun NameList(names: List<String>,modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items( items = names ) { names ->
            Greeting(name = names)
            Divider(color = Color.Black)
        }

    }
}
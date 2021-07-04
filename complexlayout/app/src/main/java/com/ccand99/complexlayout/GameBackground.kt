package com.ccand99.complexlayout

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color

@Composable
fun GameBackground(){

    Canvas(modifier = Modifier.fillMaxSize()) {
        //画背景
        drawRect(
            Color.Blue.copy(0.5f),
            size = Size(size.width,size.height*0.8F)
        )
        //画草坪
        drawRect(
            Color.Green.copy(0.8f),
            Offset(0F,size.height*0.8F),
            size = Size(size.width,size.height*0.2F)
        )
        //画太阳
        drawCircle(
            Color.Yellow,
            radius = 120F,
            center = Offset(size.width,0F)
        )
    }
}
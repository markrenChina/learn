package com.ccand99.complexlayout

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import java.util.concurrent.ThreadLocalRandom

/**
 * 游戏入口
 */
@Composable
fun Game() {
    //先画背景
    GameBackground()
    //主要内容
    GameView()

}

@Composable
fun GameView() {
    //创建一个随机发生器
    val random by remember { mutableStateOf(ThreadLocalRandom.current()) }

    //添加动画
    val infiniteTransition = rememberInfiniteTransition()
    val animatedProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(25000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    val heroProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    //创建障碍物
    val blockList = remember {
        val offsets = mutableListOf<Block>()
        //不用随机数直接均匀分布
        val between = 1.0F / BLOCK_NUMBER
        (1..BLOCK_NUMBER).forEach { i ->
            val block = Block(Pair((between * i), if(random.nextBoolean()) 0.5F else 0.65F))
            offsets.add(block)
        }
        offsets
    }

    //障碍物
    repeat(BLOCK_NUMBER) {
        //计算当前地址
        var x = lerp(blockList[it].offset.first,blockList[it].offset.first - 1F,animatedProgress)
        if (x < 0F) {
            x += 1F
        }
        val y = blockList[it].offset.second
        Log.i("TAG", "")
        BlockView(offset = Pair(x,y))
    }

   HeroView((heroProgress / 0.2F).toInt())
}

@Composable
fun HeroView(
    progress: Int
) {
    val assets = LocalContext.current.assets
    val runBitmaps by remember {
        val bitmaps = mutableListOf<ImageBitmap>()
        (1..5).forEach {
            assets.open("run_anim$it.webp").use { ins->
                bitmaps.add(BitmapFactory.decodeStream(ins).asImageBitmap())
            }
        }
        mutableStateOf(bitmaps)
    }
    val jumpBitmap by remember {
        mutableStateOf( assets.open("jump.webp").use {
            BitmapFactory.decodeStream(it).asImageBitmap()
        })
    }
    val duckBitmap by remember {
        mutableStateOf( assets.open("duck.webp").use {
            BitmapFactory.decodeStream(it).asImageBitmap()
        })
    }

    //动画状态，只有run(0)可以触发 跳(1)，蹲(2)
    var state by remember { mutableStateOf(0)}

    val jump = remember { Animatable(0.6f) }
    //手势判断
    var offset by remember { mutableStateOf(0f) }
    LaunchedEffect(key1 = offset){
        if (state == 0) {
            if (offset < -100) {
                Log.i("TAG", "jump")
                state = 1
                offset = 0f
            } else if (offset > 100) {
                Log.i("TAG", "duck")
                state = 2
                offset = 0f
            }else state = 0
        }
    }
    LaunchedEffect(key1 = state){
        if (state == 2) {
            delay(1000)
            offset = 0f
            state = 0
        }else if (state == 1){
            jump.animateTo(
                targetValue = 0.27f,
                animationSpec =
                //tween(durationMillis = 400, easing = LinearEasing)
                spring(
                    stiffness = 30f//Spring.StiffnessVeryLow,
                )
            )
            delay(600)
            jump.animateTo(
                targetValue = 0.6f,
                animationSpec = spring(
                    stiffness = 30f//Spring.StiffnessVeryLow,
                )
            )
            delay(400)
            state = 0
            offset = 0f
        }
    }

    //Log.i("TAG", "state : $state ")
    //Log.i("TAG", "offset : $offset ")
    Canvas(modifier = Modifier
        .fillMaxSize()
        .scrollable(
            orientation = Orientation.Vertical,
            state = rememberScrollableState { delta ->
                offset += delta
                delta
            }
        )) {
        if (state == 0){
            //run
            drawImage(
                image = runBitmaps[progress],
                dstOffset = IntOffset((0.05F* size.width).toInt(),(0.6f* size.height).toInt()),
                dstSize = IntSize((size.width*0.1f).toInt(),(size.height*0.2F).toInt())
            )
        }else if (state == 1){
            //jump
            drawImage(
                image = jumpBitmap,
                dstOffset = IntOffset((0.05F* size.width ).toInt(),(jump.value* size.height).toInt()),
                dstSize = IntSize((size.width*0.1f).toInt(),(size.height*0.2F).toInt())
            )
        }else{
            //duck
            drawImage(
                image = duckBitmap,
                dstOffset = IntOffset((0.05F* size.width).toInt(),(0.65F* size.height).toInt()),
                dstSize = IntSize((size.width*0.1f).toInt(),(size.height*0.15F).toInt())
            )
        }

    }
}

const val BLOCK_NUMBER = 5

/**
 * 障碍物
 */
@Composable
fun BlockView(
    offset: Pair<Float, Float>,
) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawRect(
            Color.Yellow,
            topLeft = Offset(offset.first * size.width, offset.second * size.height),
            size = Size(size.width*0.03F, size.height*0.15F)
        )
    }
}
//线性拟合
fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return (1 - fraction) * start + fraction * stop
}

data class Block(
    val offset: Pair<Float, Float>
)

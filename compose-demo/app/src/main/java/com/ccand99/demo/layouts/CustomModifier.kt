package com.ccand99.demo.layouts

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ccand99.demo.ui.theme.AppTheme

/**
 *  自定义约束，Modifier
 */
fun Modifier.firstBaselineToTop(
    firstBaselineToTop: Dp
) = Modifier.layout { measurable, constraints ->
    //测量子组件
    val placeable = measurable.measure(constraints = constraints)

    //Check the composable has a first baseline
    check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
    val firstBaseline = placeable[FirstBaseline]

    //Height of the composable with padding - first baseline
    val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
    val height = placeable.height + placeableY
    layout(placeable.width,height = height) {
        //where the composable gets placed
        placeable.placeRelative(0,placeableY)
    }
}

@Preview
@Composable
fun TextWithPaddingToBaselinePreview() {
    AppTheme() {
        Text("Hi there!", Modifier.firstBaselineToTop(32.dp))
    }
}

@Preview
@Composable
fun TextWithNormalPaddingPreview() {
    AppTheme() {
        Text("Hi there!", Modifier.padding(top = 32.dp))
    }
}

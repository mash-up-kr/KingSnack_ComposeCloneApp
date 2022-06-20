package com.example.kingsnack.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.kingsnack.ui.theme.KingsnackColorTheme
import kotlin.math.ln

/**
 * @author 김현국
 * @created 2022/06/13
 */

@Composable
fun KingsnackSurface(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    color: Color = KingsnackColorTheme.colors.uiBackground,
    contentColor: Color = KingsnackColorTheme.colors.textSecondary,
    border: BorderStroke? = null,
    elevation: Dp = 0.dp,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier.shadow(elevation = elevation, shape = shape, clip = false)
            .zIndex(elevation.value)
            .then(if (border != null) Modifier.border(border, shape) else Modifier)
            .background(
                color = getBackgroundColorForElevation(color, elevation),
                shape = shape
            )
            .clip(shape) // shape에 맞게 content를 자른다.
    ) {
        CompositionLocalProvider(LocalContentColor provides contentColor, content = content)
    }
}

@Composable
fun getBackgroundColorForElevation(color: Color, elevation: Dp): Color {
    return if (elevation > 0.dp) {
        color.withElevation(elevation)
    } else {
        color
    }
}

private fun Color.withElevation(elevation: Dp): Color {
    val foreground = calculateForeground(elevation)
    return foreground.compositeOver(this)
    /*
    compositeOver
    Porter-Duff 'source over' 모드를 사용하여 배경 위에 이 색상을 합성한다.
    이를 나타내는 Color는 배경 위에 합성되어 배경의 색 공간으로 변환된다.
     */
}

private fun calculateForeground(elevation: Dp): Color {
    val alpha = ((4.5f * ln(elevation.value + 1)) + 2f) / 100f
    return Color.White.copy(alpha = alpha)
}

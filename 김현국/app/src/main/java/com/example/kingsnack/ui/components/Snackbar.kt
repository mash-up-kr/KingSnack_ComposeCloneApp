package com.example.kingsnack.ui.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarData
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.kingsnack.ui.theme.KingsnackColorTheme

/**
 * @author 김현국
 * @created 2022/06/17
 */

@Composable
fun KingsnackSnackbar(
    snackbarData: SnackbarData,
    modifier: Modifier = Modifier,
    actionOnNewLine: Boolean = false,
    shape: Shape = MaterialTheme.shapes.small,
    backgroundColor: Color = KingsnackColorTheme.colors.uiBackground,
    contentColor: Color = KingsnackColorTheme.colors.textSecondary,
    actionColor: Color = KingsnackColorTheme.colors.brand,
    elevation: Dp = 6.dp
) {
    Snackbar(
        snackbarData, modifier, actionOnNewLine, shape, backgroundColor, contentColor, actionColor, elevation
    )
}

package com.example.kingsnack.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.kingsnack.ui.theme.KingSnackTheme
import com.example.kingsnack.ui.theme.KingsnackColorTheme

/**
 * @author 김현국
 * @created 2022/06/13
 */

@Composable
fun KingsnackDivider(
    modifier: Modifier = Modifier,
    color: Color = KingsnackColorTheme.colors.uiBorder.copy(alpha = DividerAlpha),
    thickness: Dp = 1.dp,
    startIndent: Dp = 0.dp
) {
    Divider(
        modifier = modifier,
        color = color,
        thickness = thickness,
        startIndent = startIndent
    )
}

private const val DividerAlpha = 0.12f

@Preview("default", showBackground = true)
@Composable
private fun DividerPreview() {
    KingSnackTheme {
        Box(Modifier.size(height = 10.dp, width = 100.dp)) {
            KingsnackDivider(Modifier.align(Alignment.Center))
        }

//
    }
}

package com.example.kingsnack.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kingsnack.ui.theme.KingSnackTheme
import com.example.kingsnack.ui.theme.KingsnackColorTheme

/**
 * @author 김현국
 * @created 2022/06/14
 */

@Composable
fun KingsnackGradientTintedIconButton(
    imageVector: ImageVector,
    onClick: () -> Unit,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    colors: List<Color> = KingsnackColorTheme.colors.interactiveSecondary
) {
    val interactionSource = remember { MutableInteractionSource() }

    val border = Modifier.fadeInDiagonalGradientBorder(
        showBorder = true,
        colors = KingsnackColorTheme.colors.interactiveSecondary,
        shape = CircleShape
    )
    val pressed by interactionSource.collectIsPressedAsState()
    val background = if (pressed) {
        Modifier.offsetGradientBackground(colors, 200f, 0f)
    } else {
        Modifier.background(KingsnackColorTheme.colors.uiBackground)
    }
    val blendMode = if (KingsnackColorTheme.colors.isDark) BlendMode.Darken else BlendMode.Plus
    val modifierColor = if (pressed) {
        Modifier.diagonalGradientTint(
            colors = listOf(
                KingsnackColorTheme.colors.textSecondary,
                KingsnackColorTheme.colors.textSecondary
            ),
            blendMode = blendMode
        )
    } else {
        Modifier.diagonalGradientTint(
            colors = colors,
            blendMode = blendMode
        )
    }
    Surface(
        modifier = modifier
            .clickable(
                onClick = onClick,
                interactionSource = interactionSource,
                indication = null
            )
            .clip(CircleShape)
            .then(border)
            .then(background),
        color = Color.Transparent
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            modifier = modifierColor
        )
    }
}
@Preview("default")
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun GradientTintedIconButtonPreview() {
    KingSnackTheme {
        KingsnackGradientTintedIconButton(
            imageVector = Icons.Default.Add,
            onClick = { /*TODO*/ },
            contentDescription = "Demo",
            modifier = Modifier.padding(4.dp)
        )
    }
}

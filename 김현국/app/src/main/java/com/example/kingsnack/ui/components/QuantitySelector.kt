package com.example.kingsnack.ui.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kingsnack.R
import com.example.kingsnack.ui.theme.KingSnackTheme
import com.example.kingsnack.ui.theme.KingsnackColorTheme

/**
 * @author 김현국
 * @created 2022/06/14
 */
@Composable
fun QuantitySelector(
    count: Int,
    decreaseItemCount: () -> Unit,
    increaseItemCount: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = stringResource(id = R.string.quantity),
                style = MaterialTheme.typography.subtitle1,
                color = KingsnackColorTheme.colors.textSecondary,
                modifier = Modifier
                    .padding(end = 18.dp)
                    .align(Alignment.CenterVertically)
            )
        }
        KingsnackGradientTintedIconButton(
            imageVector = Icons.Default.Remove,
            onClick = decreaseItemCount, contentDescription = stringResource(id = R.string.label_decrease),
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Crossfade(
            targetState = count,
            modifier = Modifier
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = "$it",
                style = MaterialTheme.typography.subtitle2,
                fontSize = 18.sp,
                color = KingsnackColorTheme.colors.textPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier.widthIn(min = 24.dp)
            )
        }
        KingsnackGradientTintedIconButton(
            imageVector = Icons.Default.Add,
            onClick = increaseItemCount,
            contentDescription = stringResource(id = R.string.label_increase),
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}
@Preview("default")
@Composable
fun QuantitySelectorPreview() {
    KingSnackTheme() {
        KingsnackSurface() {
            QuantitySelector(
                count = 1,
                decreaseItemCount = { },
                increaseItemCount = {}
            )
        }
    }
}
@Preview("default")
@Composable
fun QuantitySelectorPreviewRtl() {
    KingSnackTheme() {
        KingsnackSurface() {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                QuantitySelector(
                    count = 1,
                    decreaseItemCount = { /*TODO*/ },
                    increaseItemCount = { /*TODO*/ }
                )
            }
        }
    }
}

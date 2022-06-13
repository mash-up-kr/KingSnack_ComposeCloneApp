package com.example.kingsnack.ui.components

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kingsnack.R
import com.example.kingsnack.model.Filter
import com.example.kingsnack.ui.theme.KingSnackTheme
import com.example.kingsnack.ui.theme.KingsnackColorTheme

/**
 * @author 김현국
 * @created 2022/06/13
 */

@Composable
fun FilterBar(
    filters: List<Filter>,
    onShowFilters: () -> Unit
) {
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.heightIn(min = 56.dp)
    ) {
        item {
            IconButton(onClick = onShowFilters) {
                Icon(
                    imageVector = Icons.Rounded.FilterList,
                    tint = KingsnackColorTheme.colors.brand,
                    contentDescription = stringResource(id = R.string.label_filters),
                    modifier = Modifier.diagonalGradientBorder(
                        colors = KingsnackColorTheme.colors.interactiveSecondary,
                        shape = CircleShape
                    )
                )
            }
        }
        items(filters) { filters ->
        }
    }
}

@Composable
fun FilterChip(
    filter: Filter,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.small // Round corner shape 4.dp
) {
    val (selected, setSelected) = filter.enabled
    val backgroundColor by animateColorAsState(
        if (selected) KingsnackColorTheme.colors.brandSecondary else KingsnackColorTheme.colors.uiBackground
    )
    val border = Modifier.fadeInDiagonalGradientBorder(
        showBorder = !selected,
        colors = KingsnackColorTheme.colors.interactiveSecondary,
        shape = shape
    )
    val textColor by animateColorAsState(
        if (selected) Color.Black else KingsnackColorTheme.colors.textSecondary
    )

    KingsnackSurface(
        modifier = modifier.height(28.dp),
        color = backgroundColor,
        contentColor = textColor,
        shape = shape,
        elevation = 2.dp
    ) {
        val interactionSource = remember { MutableInteractionSource() }
        /*
        Interactions에는 구성 요소를 누르거나 끌 때와 같은 다양한 상태에서 구성 요소가 표시되는 방식을 변경하는데 사용
         */
        val pressed by interactionSource.collectIsPressedAsState()
        val backgroundPressed = if (pressed) {
            Modifier.offsetGradientBackground(
                KingsnackColorTheme.colors.interactiveSecondary,
                200f,
                0f
            )
        } else {
            Modifier.background(Color.Transparent)
        }
        Box(
            modifier = Modifier
                .toggleable(
                    value = selected,
                    onValueChange = setSelected,
                    interactionSource = interactionSource,
                    indication = null
                ).then(backgroundPressed)
                .then(border),
        ) {
            Text(
                text = filter.name,
                style = MaterialTheme.typography.caption,
                maxLines = 1,
                modifier = Modifier.padding(
                    horizontal = 20.dp,
                    vertical = 6.dp
                )
            )
        }
    }
}

@Preview("default")
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("large font", fontScale = 2f)
@Composable
private fun FilterDisabledPreview() {
    KingSnackTheme() {
        FilterChip(Filter(name = "Demo", enabled = false), Modifier.padding(4.dp))
    }
}

@Preview("default")
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("large font", fontScale = 2f)
@Composable
private fun FilterEnabledPreview() {
    KingSnackTheme() {
        FilterChip(Filter(name = "Demo", enabled = false), Modifier.padding(4.dp))
    }
}

package com.example.kingsnack.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout

/**
 * @author 김현국
 * @created 2022/06/16
 */
@Composable
fun VerticalGrid(
    modifier: Modifier = Modifier,
    columns: Int = 2,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->

        val itemWidth = constraints.maxWidth / columns

        val itemConstriants = constraints.copy(
            minWidth = itemWidth,
            minHeight = itemWidth
        )

        val placeables = measurables.map { it.measure(itemConstriants) }

        val columnHeights = Array(columns) { 0 }
        placeables.forEachIndexed { index, placeable ->
            val column = index % columns
            columnHeights[column] += placeable.height
        }
        val height = (columnHeights.maxOrNull() ?: constraints.minHeight).coerceAtMost(constraints.maxHeight)

        layout(width = constraints.maxWidth, height = height) {
            val columnY = Array(columns) { 0 }
            placeables.forEachIndexed { index, placeable ->
                val column = index % columns
                placeable.placeRelative(
                    x = column * itemWidth,
                    y = columnY[column]
                )
                columnY[column] += placeable.height
            }
        }
    }
}

package com.example.kingsnack.ui.home.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.example.kingsnack.model.SearchCategory
import com.example.kingsnack.model.SearchCategoryCollection
import com.example.kingsnack.ui.components.SnackImage
import com.example.kingsnack.ui.components.VerticalGrid
import com.example.kingsnack.ui.theme.KingSnackTheme
import com.example.kingsnack.ui.theme.KingsnackColorTheme
import kotlin.math.max

/**
 * @author 김현국
 * @created 2022/06/16
 */

@Composable
fun SearchCategories(
    categories: List<SearchCategoryCollection>
) {
    LazyColumn {
        itemsIndexed(categories) { index, collection ->
            SearchCategoryCollection(collection, index)
        }
    }
}

@Composable
private fun SearchCategoryCollection(
    collection: SearchCategoryCollection,
    index: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(
            text = collection.name,
            style = MaterialTheme.typography.h6,
            color = KingsnackColorTheme.colors.textPrimary,
            modifier = Modifier
                .heightIn(min = 56.dp)
                .padding(horizontal = 24.dp, vertical = 4.dp)
                .wrapContentHeight()
        )
        VerticalGrid(Modifier.padding(horizontal = 16.dp)) {
            val gradient = when (index % 2) {
                0 -> KingsnackColorTheme.colors.gradient2_2
                else -> KingsnackColorTheme.colors.gradient2_3
            }
            collection.categories.forEach { category ->
                SearchCategory(
                    category = category,
                    gradient = gradient,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
        Spacer(Modifier.height(4.dp))
    }
}
private val MinImageSize = 134.dp
private val CategoryShape = RoundedCornerShape(10.dp)
private const val CategoryTextProportion = 0.55f

@Composable
private fun SearchCategory(
    category: SearchCategory,
    gradient: List<Color>,
    modifier: Modifier = Modifier
) {
    Layout(
        modifier = modifier
            .aspectRatio(1.45f)
            .shadow(
                elevation = 3.dp,
                shape = CategoryShape
            )
            .clip(CategoryShape)
            .background(Brush.horizontalGradient(gradient))
            .clickable { },
        content = {
            Text(
                text = category.name,
                style = MaterialTheme.typography.subtitle1,
                color = KingsnackColorTheme.colors.textSecondary,
                modifier = Modifier
                    .padding(4.dp)
                    .padding(start = 8.dp)
            )
            SnackImage(
                imageUrl = category.imageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
    ) { measurables, constraints ->

        val textWidth = (constraints.maxWidth * CategoryTextProportion).toInt()
        val textPlaceable = measurables[0].measure(Constraints.fixedWidth(textWidth))

        val imageSize = max(MinImageSize.roundToPx(), constraints.maxHeight)
        val imagePlaceable = measurables[1].measure(Constraints.fixed(imageSize, imageSize))
        layout(
            width = constraints.maxWidth,
            height = constraints.minHeight
        ) {
            textPlaceable.placeRelative(
                x = 0,
                y = (constraints.maxHeight - textPlaceable.height) / 2
            )
            imagePlaceable.placeRelative(
                x = textWidth,
                y = (constraints.maxHeight - imagePlaceable.height) / 2
            )
        }
    }
}

@Preview
@Composable
private fun SearchCategoryPreview() {
    KingSnackTheme() {
        SearchCategory(
            category = SearchCategory(
                name = "Desserts",
                imageUrl = ""
            ),
            gradient = KingsnackColorTheme.colors.gradient3_2
        )
//
    }
}

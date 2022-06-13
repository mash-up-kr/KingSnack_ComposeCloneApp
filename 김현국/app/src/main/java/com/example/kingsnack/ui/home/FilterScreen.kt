package com.example.kingsnack.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.kingsnack.R
import com.example.kingsnack.model.Filter
import com.example.kingsnack.model.SnackRepo
import com.example.kingsnack.ui.components.FilterChip
import com.example.kingsnack.ui.components.KingsnackScaffold
import com.example.kingsnack.ui.theme.KingsnackColorTheme
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
/**
 * @author 김현국
 * @created 2022/06/13
 */
@Composable
fun FilterScreen(
    onDismiss: () -> Unit
) {
    var sortState by remember { mutableStateOf(SnackRepo.getSortDefault()) }
    var maxCalories by remember { mutableStateOf(0f) }
    val defaultFilter = SnackRepo.getSortDefault()

    Dialog(onDismissRequest = onDismiss) {
        val priceFilter = remember { SnackRepo.getPriceFilter() }
        val categoryFilters = remember { SnackRepo.getCategoryFilters() }
        val lifeStyleFilters = remember { SnackRepo.getLifeStyleFilters() }
        KingsnackScaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = onDismiss) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = stringResource(id = R.string.close)
                            )
                        }
                    },
                    title = {
                        Text(
                            text = stringResource(id = R.string.label_filters),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.h6
                        )
                    },
                    actions = {
                        var resetEnabled = sortState != defaultFilter
                        IconButton(
                            onClick = { /*TODO*/ },
                            enabled = resetEnabled
                        ) {
                            val alpha = if (resetEnabled) {
                                ContentAlpha.high
                            } else {
                                ContentAlpha.disabled
                            }
                            CompositionLocalProvider(LocalContentAlpha provides alpha) {
                                Text(
                                    text = stringResource(id = R.string.reset),
                                    style = MaterialTheme.typography.body2
                                )
                            }
                        }
                    },
                    backgroundColor = KingsnackColorTheme.colors.uiBackground
                )
            }
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 16.dp),
            ) {

//
            }
        }
    }
}

@Composable
fun SortFiltersSection(sortState: String, onFilterChange: (Filter) -> Unit) {
    FilterTitle(text = stringResource(id = R.string.sort))
    Column(Modifier.padding(bottom = 24.dp)) {
    }
}

@Composable
fun SortFilters(
    sortFilters: List<Filter> = SnackRepo.getSortFilters(),
    sortState: String,
    onChanged: (Filter) -> Unit
) {
    sortFilters.forEach { filter ->
    }
}

@Composable
fun SortOption(
    text: String,
    icon: ImageVector?,
    onClickOption: () -> Unit,
    selected: Boolean
) {
    Row(
        modifier = Modifier
            .padding(top = 14.dp)
            .selectable(selected) { onClickOption() }
    ) {

    }
}

@Composable
fun FilterChipSection(title: String, filters: List<Filter>) {
    FilterTitle(text = title)
    FlowRow(
        mainAxisAlignment = FlowMainAxisAlignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, bottom = 16.dp)
            .padding(horizontal = 4.dp)
    ) {
        filters.forEach { filter ->
            FilterChip(
                filter = filter,
                modifier = Modifier.padding(end = 4.dp, bottom = 8.dp)
            )
        }
    }
}
@Composable
fun FilterTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.h6,
        color = KingsnackColorTheme.colors.brand,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Preview("filter screen")
@Composable
fun FilterScreenPreview() {
    FilterScreen(onDismiss = {})
}

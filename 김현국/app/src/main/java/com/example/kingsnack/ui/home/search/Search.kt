package com.example.kingsnack.ui.home.search

import android.app.appsearch.SearchResults
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kingsnack.R
import com.example.kingsnack.model.*
import com.example.kingsnack.ui.components.KingsnackDivider
import com.example.kingsnack.ui.components.KingsnackSurface
import com.example.kingsnack.ui.theme.KingSnackTheme
import com.example.kingsnack.ui.theme.KingsnackColorTheme
import com.example.kingsnack.utils.mirroringBackIcon

/**
 * @author 김현국
 * @created 2022/06/15
 */

@Composable
fun Search(
    onSnackClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    state: SearchState = rememberSearchState()
) {
    KingsnackSurface(modifier = modifier.fillMaxSize()) {
        Column {
            Spacer(modifier = Modifier.statusBarsPadding())
            SearchBar(
                query = state.query,
                onQueryChange = { state.query = it },
                searchFocused = state.focused,
                onSearchFocusChange = { state.focused = it },
                onClearQuery = { state.query = TextFieldValue("") },
                searching = state.searching
            )
            KingsnackDivider()

            LaunchedEffect(state.query.text) {
                state.searching = true
                state.searchResults = SearchRepo.search(state.query.text)
                state.searching = false
            }
            when (state.searchDisplay) {
                SearchDisplay.Categories -> SearchCategories(state.categories)
                SearchDisplay.Suggestions -> SearchSuggestions(
                    suggestions = state.suggesstions,
                    onSuggestionSelect = { suggestion -> state.query = TextFieldValue(suggestion) }
                )
                SearchDisplay.Results -> SearchResults(
                    state.searchResults,
                    state.filters,
                    onSnackClick
                )
                SearchDisplay.NoResults -> NoResults(state.query.text)
            }
        }
//
    }
}

@Composable
private fun rememberSearchState(
    query: TextFieldValue = TextFieldValue(""),
    focused: Boolean = false,
    searching: Boolean = false,
    categories: List<SearchCategoryCollection> = SearchRepo.getCategories(),
    suggestions: List<SearchSuggestionGroup> = SearchRepo.getSuggestions(),
    filters: List<Filter> = SnackRepo.getFilters(),
    searchResults: List<Snack> = emptyList()
): SearchState {
    return remember {
        SearchState(
            query, focused, searching, categories, suggestions, filters, searchResults

        )
    }
}

enum class SearchDisplay {
    Categories, Suggestions, Results, NoResults
}
@Stable
class SearchState(
    query: TextFieldValue,
    focused: Boolean,
    searching: Boolean,
    categories: List<SearchCategoryCollection>,
    suggestions: List<SearchSuggestionGroup>,
    filters: List<Filter>,
    searchResults: List<Snack>
) {
    var query by mutableStateOf(query)
    var focused by mutableStateOf(focused)
    var searching by mutableStateOf(searching)
    var categories by mutableStateOf(categories)
    var suggesstions by mutableStateOf(suggestions)
    var filters by mutableStateOf(filters)
    var searchResults by mutableStateOf(searchResults)
    val searchDisplay: SearchDisplay
        get() = when {
            !focused && query.text.isEmpty() -> SearchDisplay.Categories
            focused && query.text.isEmpty() -> SearchDisplay.Suggestions
            searchResults.isEmpty() -> SearchDisplay.NoResults
            else -> SearchDisplay.Results
        }
}

@Composable
private fun SearchBar(
    query: TextFieldValue,
    onQueryChange: (TextFieldValue) -> Unit,
    searchFocused: Boolean,
    onSearchFocusChange: (Boolean) -> Unit,
    onClearQuery: () -> Unit,
    searching: Boolean,
    modifier: Modifier = Modifier
) {
    KingsnackSurface(
        color = KingsnackColorTheme.colors.uiFloated,
        contentColor = KingsnackColorTheme.colors.textSecondary,
        shape = MaterialTheme.shapes.small,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 24.dp, vertical = 8.dp)
    ) {
        Box(Modifier.fillMaxSize()) {
            if (query.text.isEmpty()) {
                SearchHint()
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight()
            ) {
                if (searchFocused) {
                    IconButton(onClick = onClearQuery) {
                        Icon(
                            imageVector = mirroringBackIcon(),
                            tint = KingsnackColorTheme.colors.iconPrimary,
                            contentDescription = stringResource(id = R.string.label_back)
                        )
                    }
                }
                BasicTextField(
                    value = query, onValueChange = onQueryChange,
                    modifier = Modifier.weight(1f).onFocusChanged {
                        onSearchFocusChange(it.isFocused)
                    }
                )
                if (searching) {
                    CircularProgressIndicator(
                        color = KingsnackColorTheme.colors.iconPrimary,
                        modifier = Modifier
                            .padding(horizontal = 6.dp)
                            .size(36.dp)
                    )
                } else {
                    Spacer(Modifier.width(IconSize))
                }
            }
        }
    }
}

private val IconSize = 48.dp

@Composable
private fun SearchHint() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize()
    ) {
        Icon(
            imageVector = Icons.Outlined.Search,
            tint = KingsnackColorTheme.colors.textHelp,
            contentDescription = stringResource(id = R.string.label_search)
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = stringResource(id = R.string.search_jetsnack),
            color = KingsnackColorTheme.colors.textHelp
        )
    }
}

@Preview
@Composable
private fun SearchBarPreview() {
    KingSnackTheme() {
        KingsnackSurface() {
            SearchBar(
                query = TextFieldValue(""),
                onQueryChange = {},
                searchFocused = false,
                onSearchFocusChange = {},
                onClearQuery = { /*TODO*/ },
                searching = false
            )
//
        }

//
    }
}

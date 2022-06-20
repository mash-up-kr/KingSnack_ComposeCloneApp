package com.example.kingsnack

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.kingsnack.ui.components.KingsnackScaffold
import com.example.kingsnack.ui.components.KingsnackSnackbar
import com.example.kingsnack.ui.home.HomeSections
import com.example.kingsnack.ui.home.KingsnackBottomBar
import com.example.kingsnack.ui.home.addHomeGraph
import com.example.kingsnack.ui.snackdetail.SnackDetail
import com.example.kingsnack.ui.theme.KingSnackTheme

/**
 * @author 김현국
 * @created 2022/06/08
 */

@Composable
fun KingsnackApp() {
    KingSnackTheme {
        val appState = rememberKingsnackAppState()
        KingsnackScaffold(
            bottomBar = {
                if (appState.shouldShowBottomBar) {
                    KingsnackBottomBar(
                        tabs = appState.bottomBarTabs,
                        currentRoute = appState.currentRoute!!,
                        navigateToRoute = appState::navigateToBottomBarRoute
                    )
                }
            },
            snackbarHost = {
                SnackbarHost(
                    hostState = it,
                    modifier = Modifier.systemBarsPadding(),
                    snackbar = { snackbarData -> KingsnackSnackbar(snackbarData) }

                )
            },
            scaffoldState = appState.scaffoldState
        ) { innerPaddingModifier ->
            NavHost(
                navController = appState.navController,
                startDestination = MainDestinations.HOME_ROUTE,
                modifier = Modifier.padding(innerPaddingModifier)
            ) {
                kingsnackNavGraph(
                    onSnackSelected = appState::navigateToSnackDetail,
                    upPress = appState::upPress
                )
            }
        }
    }
}
private fun NavGraphBuilder.kingsnackNavGraph(
    onSnackSelected: (Long, NavBackStackEntry) -> Unit,
    upPress: () -> Unit
) {
    navigation(
        route = MainDestinations.HOME_ROUTE,
        startDestination = HomeSections.FEED.route
    ) {
        addHomeGraph(onSnackSelected)
    }
    composable(
        "${MainDestinations.SNACK_DETAIL_ROUTE}/{${MainDestinations.SNACK_ID_KEY}}",
        arguments = listOf(navArgument(MainDestinations.SNACK_ID_KEY) { type = NavType.LongType })
    ) { backStackEntry ->
        val arguments = requireNotNull(backStackEntry.arguments)
        val snackId = arguments.getLong(MainDestinations.SNACK_ID_KEY)
        SnackDetail(snackId, upPress)
    }
}

package com.example.kingsnack

import android.content.res.Resources
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.kingsnack.model.SnackbarManager
import com.example.kingsnack.ui.home.HomeSections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * @author 김현국
 * @created 2022/06/08
 */

/**
 * @author 김현국
 * @created 2022/06/08
 */

/*
    JetSnackApp에서 사용되는 목적지들
 */
object MainDestinations {
    const val HOME_ROUTE = "home"
    const val SNACK_DETAIL_ROUTE = "snack"
    const val SNACK_ID_KEY = "snackId"
}
/*
    JetsnackAppState의 인스턴스를 기억하고 생성한다.
 */
@Composable
fun rememberKingsnackAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    snackbarManager: SnackbarManager = SnackbarManager,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) = remember(scaffoldState, navController, snackbarManager, resources, coroutineScope) {
    KingsnackAppState(scaffoldState, navController, snackbarManager, resources, coroutineScope)
}

/*
    [JetsnackApp]과 관련된 상태를 유지하고 UI관련 로직을담는 역할을 한다.
 */
@Stable
class KingsnackAppState(
    val scaffoldState: ScaffoldState,
    val navController: NavHostController,
    private val snackbarManager: SnackbarManager, // Model
    private val resources: Resources,
    coroutineScope: CoroutineScope
) {
    // SnackbarManager으로 부터 오는 snackbars 처리
    init {
        coroutineScope.launch {
            snackbarManager.messages.collect { currentMessages ->
                if (currentMessages.isNotEmpty()) {
                    val message = currentMessages[0]
                    val text = resources.getText(message.messageId)

                    /*
                    화면에 스넥바를 표시한다. 'showSnackbar'는 스낵바가 화면에서 사라질 때까지 지연되는 function
                     */
                    scaffoldState.snackbarHostState.showSnackbar(text.toString())
                    /*
                    스낵바가 사라지거나 없어졌을때 스낵바 manager에게 알린다.
                     */
                    snackbarManager.setMessageShown(message.id)
                }
            }
        }
    }

    val bottomBarTabs = HomeSections.values()
    private val bottomBarRoutes = bottomBarTabs.map { it.route }

    /*
    BottomBar state
    이 속성을 읽으면 하단 막대가 표시되어야하는지 여부에 따라 재구성이 발생한다.
    모든 목적지가 바텀바를 보여줄 필요성은 없다.
     */
    val shouldShowBottomBar: Boolean
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination?.route in bottomBarRoutes

    /*
    Navigation state
     */
    val currentRoute: String?
        get() = navController.currentDestination?.route

    fun upPress() {
        navController.navigateUp()
    }

    fun navigateToBottomBarRoute(route: String) {
        if (route != currentRoute) {
            navController.navigate(route) {

                // 같은 아이템을 눌렀을 때 같은 목적지를 여러번 복사하는 것을 피함
                launchSingleTop = true

                // 이전의 선택된 아이템을 다시 선택했을 때, 상태를 재저장하고 복원된다.
                restoreState = true
                /*
                 백스택이 첫번째 대상을 팝업하고 상태를 저장.
                 이것은 어떤 bottom tab에서 뒤로가기를 했을때 start destination으로 이동하게 한다.
                 */
                popUpTo(findStartDestination(navController.graph).id) {
                    saveState = true
                }
            }
        }
    }

    fun navigateToSnackDetail(snackId: Long, from: NavBackStackEntry) {
        if (from.lifecycleIsResumed()) {
            navController.navigate("${MainDestinations.SNACK_DETAIL_ROUTE}/$snackId")
        }
    }
}

// 중복된 탐색 이벤트를 버리기 위해 수명주기를 확인한다.
private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED

// 수명주기 resumed가 아니라면 이 NavBackStackEntry가 이미 탐색 이벤트를 처리했음을 의미한다.
private val NavGraph.startDestination: NavDestination?
    get() = findNode(startDestinationId)

private tailrec fun findStartDestination(graph: NavDestination): NavDestination {
    return if (graph is NavGraph) findStartDestination(graph.startDestination!!) else graph
}

/*
resources의 구현은 resources() 함수의 호출자가 리소스가 변경될 때마다 다시 시작되도록하는 것이다.
사용하는 상태가 변경되면 다시 시작된다.
 */
@Composable
@ReadOnlyComposable
private fun resources(): Resources {
    LocalConfiguration.current

    /*
    https://jetc.dev/slack/2022-02-13-why-resources-strange.html

    LocalConfiguration은 Configuration에 대한 compositionLocal을 제공한다.
    .current는 Key와 관련된 CompositionLocal 값을 반환한다


    LocalConfiguration.current 을 사용하지 않지만 참조를 해야한다.
    LocalConfiguration.current 는 명시적 관찰이다 . 명시적 관찰은 "참조"를 의미한다.
    Compose는   configuration Local의 변경 사항을 감시하고 재구성하는 것을 알고 있지만,
    이는 composable에서 영향을 받는 configuration Local을 참조할 때만 그렇게 한다.

    Configuration이 변경될 때, LocalContext.current는 변경되지 않지만,
    LocalConfiguration.current 변경된다.


    만약 Configration이 getResources() 메소드가 있다면 다음과 같이 할 수 있다.
     private fun resources(): Resources{
        return LocalConfiguration.current.resources
     }
     가 훨씬 덜 이상해 보인다. 하지만 Configuration은 getResources()를 제공하지않고 이는 Context만 제공한다.
     따라서 LocalConfiguration.current를 사용하지 않아도 참조해야한다.
     */

    return LocalContext.current.resources
}

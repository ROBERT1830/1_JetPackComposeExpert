package com.example.composeexpert.coreUi.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.composeexpert.core.network.INetworkMonitor
import com.example.composeexpert.coreUi.navigation.TopLevelDestination
import com.example.composeexpert.coreUi.navigation.addNavigationRoute
import com.example.composeexpert.coreUi.navigation.favoritesNavigationRoute
import com.example.composeexpert.coreUi.navigation.mainFeedNavigationRoute
import com.example.composeexpert.coreUi.navigation.settingsNavigationRoute
import com.example.composeexpert.feature.addX.navigateToAddScreen
import com.example.composeexpert.feature.favorites.navigateToFavoriteScreen
import com.example.composeexpert.feature.mainFeed.navigateToMainFeedScreen
import com.example.composeexpert.feature.settings.navigateToSettingsScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * Function that establish the entry point of app state.
 *
 * @param navController Needed to carry out navigation at appState
 */
@Composable
fun rememberJetAppState(
    networkMonitor: INetworkMonitor,
    windowSize: WindowSizeClass? = null,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): JetAppState = remember(networkMonitor ,coroutineScope, navController) {
    JetAppState(networkMonitor, navController, coroutineScope, windowSize)
}


/**
 * Class that manages general appState (currentDestinations, topLevelDestinations,
 * general on screen app composables like dialogs, network listening, navigation management etc...)
 *
 * @currentTopLevelDestination : Here we user the currentDestination route and removeSubRoute
 * in order to have a basic route and perform actions in all screen related to a particular destination
 * (Ex: showing gradient only in those screen related to MainFeed)
 */
@Stable
class JetAppState(
    private val networkMonitor: INetworkMonitor,
    val navController: NavHostController,
    private val coroutineScope: CoroutineScope,
    private val windowSize: WindowSizeClass?,
) {

    val isOffline = networkMonitor.isDeviceOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    //only show bottomBar when is compact screen
    val shouldShowBottomBar: Boolean
        get() = windowSize?.widthSizeClass == WindowWidthSizeClass.Compact

    val shouldShowNavRail: Boolean
        get() = !shouldShowBottomBar
    //gives you the visible composable that the user is currently seeing.
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route?.removeSubRoute()) {
            mainFeedNavigationRoute -> TopLevelDestination.MAIN_FEED
            favoritesNavigationRoute -> TopLevelDestination.FAVORITES_SCREEN
            settingsNavigationRoute -> TopLevelDestination.SETTINGS_SCREEN
            addNavigationRoute -> TopLevelDestination.ADD_SCREEN
            else -> null
        }

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.values().asList()


    fun navigateToTopLevelDestination(nextTopLevelDestination: TopLevelDestination) {
        navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }.also { navOptions ->
            when (nextTopLevelDestination) {
                TopLevelDestination.MAIN_FEED ->
                    navController.navigateToMainFeedScreen(navOptions)

                TopLevelDestination.FAVORITES_SCREEN ->
                    navController.navigateToFavoriteScreen(navOptions)

                TopLevelDestination.SETTINGS_SCREEN ->
                    navController.navigateToSettingsScreen(navOptions)

                TopLevelDestination.ADD_SCREEN ->
                    navController.navigateToAddScreen(navOptions)
            }
        }
    }

//    val showBottomNavigation: Boolean
//        @Composable get() = topLevelDestinations.any {
//            currentDestination?.route.orEmpty().contains(it.route)
//        }

}
fun String.removeSubRoute() = substringBeforeLast("/")

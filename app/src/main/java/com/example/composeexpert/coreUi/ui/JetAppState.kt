package com.example.composeexpert.coreUi.ui

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
import com.example.composeexpert.coreUi.navigation.TopLevelDestination
import com.example.composeexpert.feature.addX.addNavigationRoute
import com.example.composeexpert.feature.addX.navigateToAddScreen
import com.example.composeexpert.feature.favorites.favoritesNavigationRoute
import com.example.composeexpert.feature.mainFeed.mainFeedNavigationRoute
import com.example.composeexpert.feature.favorites.navigateToFavoriteScreen
import com.example.composeexpert.feature.mainFeed.navigateToMainFeedScreen
import com.example.composeexpert.feature.settings.navigateToSettingsScreen
import com.example.composeexpert.feature.settings.settingsNavigationRoute
import kotlinx.coroutines.CoroutineScope

/**
 * Function that establish the entry point of app state.
 *
 * @param navController Needed to carry out navigation at appState
 */
@Composable
fun rememberJetAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): JetAppState = remember(coroutineScope, navController) {
    JetAppState(navController, coroutineScope)
}


/**
 * Class that manages general appState (currentDestinations, topLevelDestinations,
 * general on screen app composables like dialogs, network listening, navigation management etc...)
 */
@Stable
class JetAppState(
    private val navController: NavHostController,
    val coroutineScope: CoroutineScope
) {
    //gives you the visible composable that the user is currently seeing.
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            mainFeedNavigationRoute -> TopLevelDestination.MAIN_FEED
            favoritesNavigationRoute -> TopLevelDestination.FAVORITES_SCREEN
            settingsNavigationRoute -> TopLevelDestination.SETTINGS_SCREEN
            addNavigationRoute -> TopLevelDestination.ADD_SCREEN
            else -> null
        }

    //this could be done by using enum, so that here in only just one line we have our list.
//    val topLevelDestinations: List<TopLevelDestination2> = listOf(
//        TopLevelDestination2.MainFeedScreen,
//        TopLevelDestination2.FavoritesScreen,
//        TopLevelDestination2.SettingScreen,
//        TopLevelDestination2.AddScreen
//    )

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.values().asList()


    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
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
            when (topLevelDestination) {
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

    val showBottomNavigation: Boolean
        @Composable get () = topLevelDestinations.any {
            currentDestination?.route.orEmpty().contains(it.route)
        }

}
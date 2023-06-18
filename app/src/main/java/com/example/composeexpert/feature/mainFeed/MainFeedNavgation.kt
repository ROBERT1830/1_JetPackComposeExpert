package com.example.composeexpert.feature.mainFeed

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.navigation
import com.example.composeexpert.coreUi.navigation.JetAppFeature
import com.example.composeexpert.coreUi.navigation.NavigationCommand
import com.example.composeexpert.coreUi.navigation.jetAppComposable
import com.example.composeexpert.coreUi.ui.JetAppState
import com.example.composeexpert.feature.mainFeed.detail.MainFeedDetailsScreen
import com.example.composeexpert.feature.mainFeed.list.MainFeedScreen

fun NavController.navigateToMainFeedScreen(navOptions: NavOptions? = null) {
    this.navigate(JetAppFeature.MAIN.route, navOptions)
}

fun NavGraphBuilder.mainFeedNav(jetAppState: JetAppState) {
    navigation(
        startDestination = NavigationCommand.GoToMain(JetAppFeature.MAIN).route,
        route = JetAppFeature.MAIN.route
    ) {
        jetAppComposable(NavigationCommand.GoToMain(JetAppFeature.MAIN)) {
            MainFeedScreen {
                jetAppState.navController.navigate(
                    NavigationCommand.GoToDetail(JetAppFeature.MAIN).createRoute(it)
                )
            }
        }
        jetAppComposable(NavigationCommand.GoToDetail(JetAppFeature.MAIN)) {
            MainFeedDetailsScreen()
        }
    }
}
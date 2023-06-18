package com.example.composeexpert.feature.favorites

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.example.composeexpert.coreUi.navigation.JetAppFeature
import com.example.composeexpert.coreUi.navigation.NavigationCommand
import com.example.composeexpert.coreUi.navigation.jetAppComposable
import com.example.composeexpert.coreUi.ui.JetAppState

fun NavController.navigateToFavoriteScreen(navOptions: NavOptions? = null) {
    this.navigate(JetAppFeature.FAVORITES.route, navOptions)
}

fun NavGraphBuilder.favoritesNav(jetAppState: JetAppState) {
    navigation(
        startDestination = NavigationCommand.GoToMain(JetAppFeature.FAVORITES).route,
        route = JetAppFeature.FAVORITES.route
    ) {
        jetAppComposable(NavigationCommand.GoToMain(JetAppFeature.FAVORITES)) {
            FavoritesScreen()
        }
    }
}
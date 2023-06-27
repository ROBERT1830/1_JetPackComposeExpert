package com.example.composeexpert.feature.settings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.example.composeexpert.coreUi.navigation.JetAppFeature
import com.example.composeexpert.coreUi.navigation.NavigationCommand
import com.example.composeexpert.coreUi.navigation.jetAppComposable
import com.example.composeexpert.coreUi.ui.JetAppState

fun NavController.navigateToSettingsScreen(navOptions: NavOptions? = null) {
    this.navigate(JetAppFeature.SETTINGS.route, navOptions)
}

fun NavGraphBuilder.settingsScreen(jetAppState: JetAppState) {
    navigation(
        startDestination = NavigationCommand.GoToMain(JetAppFeature.SETTINGS).route,
        route = JetAppFeature.SETTINGS.route
    ) {
        jetAppComposable(NavigationCommand.GoToMain(JetAppFeature.SETTINGS)) {
            SettingsScreen()
        }
    }
}
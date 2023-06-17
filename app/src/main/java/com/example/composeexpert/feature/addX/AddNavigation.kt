package com.example.composeexpert.feature.addX

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.example.composeexpert.coreUi.navigation.JetAppFeature
import com.example.composeexpert.coreUi.navigation.NavigationCommand
import com.example.composeexpert.coreUi.navigation.jetAppComposable
import com.example.composeexpert.coreUi.ui.JetAppState

fun NavController.navigateToAddScreen(navOptions: NavOptions? = null) {
    this.navigate(JetAppFeature.ADD.route, navOptions)
}

fun NavGraphBuilder.addNav(jetAppState: JetAppState) {
    navigation(
        startDestination = NavigationCommand.GoToMain(JetAppFeature.ADD).route,
        route = JetAppFeature.ADD.route
    ) {
        jetAppComposable(NavigationCommand.GoToMain(JetAppFeature.ADD)) {
            AddScreen()
        }
    }
}
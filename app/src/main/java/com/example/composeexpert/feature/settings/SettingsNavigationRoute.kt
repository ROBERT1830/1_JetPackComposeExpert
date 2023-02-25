package com.example.composeexpert.feature.settings

import androidx.navigation.NavController
import androidx.navigation.NavOptions

const val settingsNavigationRoute = "settings_route"

fun NavController.navigateToSettingsScreen(navOptions: NavOptions? = null) {
    this.navigate(settingsNavigationRoute, navOptions)
}
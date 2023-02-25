package com.example.composeexpert.feature.favorites

import androidx.navigation.NavController
import androidx.navigation.NavOptions

const val mainFeedNavigationRoute = "main_feed_route"

fun NavController.navigateToMainFeedScreen(navOptions: NavOptions? = null) {
    this.navigate(mainFeedNavigationRoute, navOptions)
}
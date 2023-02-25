package com.example.composeexpert.feature.addX

import androidx.navigation.NavController
import androidx.navigation.NavOptions

const val addNavigationRoute = "add_route"

fun NavController.navigateToAddScreen(navOptions: NavOptions? = null) {
    this.navigate(addNavigationRoute, navOptions)
}
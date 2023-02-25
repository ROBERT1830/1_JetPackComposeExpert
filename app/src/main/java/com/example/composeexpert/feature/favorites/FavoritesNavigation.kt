package com.example.composeexpert.feature.favorites

import androidx.navigation.NavController
import androidx.navigation.NavOptions

const val favoritesNavigationRoute = "favorites_route"

fun NavController.navigateToFavoriteScreen(navOptions: NavOptions? = null) {
    this.navigate(favoritesNavigationRoute, navOptions)
}
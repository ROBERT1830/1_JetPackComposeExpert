package com.example.composeexpert.coreUi.navigation

const val mainFeedNavigationRoute = "main_feed_route"
const val favoritesNavigationRoute = "favorites_route"
const val settingsNavigationRoute = "settings_route"
const val addNavigationRoute = "add_route"

enum class JetAppFeature(val route: String) {
    MAIN(mainFeedNavigationRoute),
    FAVORITES(favoritesNavigationRoute),
    SETTINGS(settingsNavigationRoute),
    ADD(addNavigationRoute)
}
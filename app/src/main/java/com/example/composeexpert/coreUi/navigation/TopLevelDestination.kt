package com.example.composeexpert.coreUi.navigation

import com.example.composeexpert.R
import com.example.composeexpert.coreUi.designSystem.Icon
import com.example.composeexpert.coreUi.designSystem.JetIcons
import com.example.composeexpert.feature.addX.addNavigationRoute
import com.example.composeexpert.feature.favorites.favoritesNavigationRoute
import com.example.composeexpert.feature.mainFeed.mainFeedNavigationRoute
import com.example.composeexpert.feature.settings.settingsNavigationRoute

enum class TopLevelDestination(
    val selectedIcon: Icon,
    val unselectedIcon: Icon,
    val iconLabel: Int,
    val screenTitle: Int,
    val route: String
) {
    MAIN_FEED(Icon.ImageVectorIcon(JetIcons.feedBottomIconFilled),
        Icon.ImageVectorIcon(JetIcons.feedBottomIconBorder),
        R.string.main_feed_screen_id,
        R.string.main_feed_screen_title,
        mainFeedNavigationRoute
    ),
    FAVORITES_SCREEN(Icon.ImageVectorIcon(JetIcons.favoriteBottomIconFilled),
        Icon.ImageVectorIcon(JetIcons.favoriteBottomIconOutlined),
        R.string.favorites_screen_id,
        R.string.favorites_screen_title,
        favoritesNavigationRoute
    ),
    SETTINGS_SCREEN( Icon.ImageVectorIcon(JetIcons.settingsBottomIconFilled),
        Icon.ImageVectorIcon(JetIcons.settingsBottomIconOutlined),
        R.string.settings_screen_id,
        R.string.settings_screen_title,
        settingsNavigationRoute
    ),
    ADD_SCREEN(Icon.ImageVectorIcon(JetIcons.addBottomIconFilled),
        Icon.ImageVectorIcon(JetIcons.addBottomIconOutlined),
        R.string.add_screen_id,
        R.string.add_screen_title,
        addNavigationRoute
    )
}


sealed class TopLevelDestination2(
    val selectedIcon: Icon,
    val unselectedIcon: Icon,
    val screenId: Int,
    val screenTitle: Int
) {
    object MainFeedScreen : TopLevelDestination2(
        Icon.ImageVectorIcon(JetIcons.feedBottomIconFilled),
        Icon.ImageVectorIcon(JetIcons.feedBottomIconBorder),
        R.string.main_feed_screen_id,
        R.string.main_feed_screen_title
    )
    object FavoritesScreen : TopLevelDestination2(
        Icon.ImageVectorIcon(JetIcons.favoriteBottomIconFilled),
        Icon.ImageVectorIcon(JetIcons.favoriteBottomIconOutlined),
        R.string.favorites_screen_id,
        R.string.favorites_screen_title
    )
    object SettingScreen : TopLevelDestination2(
        Icon.ImageVectorIcon(JetIcons.settingsBottomIconFilled),
        Icon.ImageVectorIcon(JetIcons.settingsBottomIconOutlined),
        R.string.settings_screen_id,
        R.string.settings_screen_title
    )
    object AddScreen : TopLevelDestination2(
        Icon.ImageVectorIcon(JetIcons.addBottomIconFilled),
        Icon.ImageVectorIcon(JetIcons.addBottomIconOutlined),
        R.string.add_screen_id,
        R.string.add_screen_title
    )
}
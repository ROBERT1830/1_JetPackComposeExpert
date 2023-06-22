package com.example.composeexpert.coreUi.navigation

import com.example.composeexpert.R
import com.example.composeexpert.coreUi.designSystem.Icon
import com.example.composeexpert.coreUi.designSystem.JetIcons


enum class TopLevelDestination(
    val selectedIcon: Icon,
    val unselectedIcon: Icon,
    val screenTitleId: Int,
    val iconLabelId: Int,
    val route: String
) {
    MAIN_FEED(Icon.ImageVectorIcon(JetIcons.feedBottomIconFilled),
        Icon.ImageVectorIcon(JetIcons.feedBottomIconBorder),
        R.string.main_feed_screen_title_id,
        R.string.main_feed_screen_icon_id,
        mainFeedNavigationRoute
    ),
    FAVORITES_SCREEN(Icon.ImageVectorIcon(JetIcons.favoriteBottomIconFilled),
        Icon.ImageVectorIcon(JetIcons.favoriteBottomIconOutlined),
        R.string.favorites_screen_title_id,
        R.string.favorites_screen_icon_id,
        favoritesNavigationRoute
    ),
    SETTINGS_SCREEN( Icon.ImageVectorIcon(JetIcons.settingsBottomIconFilled),
        Icon.ImageVectorIcon(JetIcons.settingsBottomIconOutlined),
        R.string.settings_screen_title_id,
        R.string.settings_screen_icon_id,
        settingsNavigationRoute
    ),
    ADD_SCREEN(Icon.ImageVectorIcon(JetIcons.addBottomIconFilled),
        Icon.ImageVectorIcon(JetIcons.addBottomIconOutlined),
        R.string.add_screen_title_id,
        R.string.add_screen_icon_id,
        addNavigationRoute
    )
}
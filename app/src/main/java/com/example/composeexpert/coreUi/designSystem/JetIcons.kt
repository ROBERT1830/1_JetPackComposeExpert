package com.example.composeexpert.coreUi.designSystem

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Holds Material icons [ImageVector]s and custom icons asss a drawable Id
 */
object JetIcons {

    /**BottomNavigation**/
    val feedBottomIconFilled = Icons.Default.List
    val feedBottomIconBorder = Icons.Outlined.List
    val favoriteBottomIconFilled = Icons.Default.Favorite
    val favoriteBottomIconOutlined = Icons.Outlined.Favorite
    val settingsBottomIconFilled = Icons.Default.Settings
    val settingsBottomIconOutlined = Icons.Outlined.Settings
    val addBottomIconFilled = Icons.Default.Add
    val addBottomIconOutlined = Icons.Outlined.Add
}

/**
 * A sealed class to make dealing with [ImageVector] and [DrawableRes] icons easier.
 */
sealed class Icon {
    data class ImageVectorIcon(val imageVector: ImageVector): Icon()
    data class DrawableResourceIcon(@DrawableRes val id: Int): Icon()
}
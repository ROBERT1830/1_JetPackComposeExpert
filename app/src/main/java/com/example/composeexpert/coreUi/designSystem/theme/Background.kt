package com.example.composeexpert.coreUi.designSystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

/**
 * A class to model background color and tonal elevation values.
 */
@Immutable //tells that this class will produce immutable instances
data class BackGroundTheme(
    val color: Color = Color.Unspecified, //treated as transparent
    val tonalElevation: Dp = Dp.Unspecified //NaN
)
/**
 * A composition local for [BackgroundTheme].
 * uses static so that all composables where this is used will trigger
 * recomposition if the background changes.
 */
val LocalBackGroundTheme = staticCompositionLocalOf { BackGroundTheme() }


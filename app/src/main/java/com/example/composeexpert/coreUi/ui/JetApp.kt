package com.example.composeexpert.coreUi.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.composeexpert.coreUi.designSystem.component.JetBackGround
import com.example.composeexpert.coreUi.designSystem.component.JetGradientBackGround
import com.example.composeexpert.coreUi.designSystem.theme.GradientColors
import com.example.composeexpert.coreUi.designSystem.theme.LocalGradientColors
import com.example.composeexpert.coreUi.navigation.DisableOption
import com.example.composeexpert.coreUi.navigation.JetNavigation
import com.example.composeexpert.coreUi.navigation.NavigationSystem
import com.example.composeexpert.coreUi.navigation.NavigationType
import com.example.composeexpert.coreUi.navigation.TopLevelDestination

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun JetApp(
    windowSizeClass: WindowSizeClass,
    appState: JetAppState = rememberJetAppState(windowSizeClass)
) {
    //show gradient only for Main Feed
    val shouldShowGradientBackGround =
        appState.currentTopLevelDestination == TopLevelDestination.MAIN_FEED

    JetBackGround {
        JetGradientBackGround(
            gradientColors = if (shouldShowGradientBackGround) {
                LocalGradientColors.current
            } else {
                GradientColors()
            }
        ) {
            JetScaffold(appState) { paddingValues ->

                JetAppContent(paddingValues = paddingValues) {

                    if (appState.shouldShowNavRail) { NavRail(appState = appState) }

                    Column(Modifier.fillMaxSize()) { JetNavigation(jetAppState = appState) }
                }
            }
        }
    }
}

/**
 * @contentWindowInsets = WindowInsets(0, 0, 0, 0) means that no window insets
 * will be applied to the content area of the Scaffold.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JetScaffold(appState: JetAppState, content: @Composable (PaddingValues) -> Unit) {
    Scaffold(
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            if (appState.shouldShowBottomBar) { BottomBar(appState) }
        }
    ) {
        content(it)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun JetAppContent(
    paddingValues: PaddingValues,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .consumeWindowInsets(paddingValues)
            .windowInsetsPadding(
                WindowInsets.safeDrawing.only(
                    WindowInsetsSides.Horizontal
                )
            ),
        content = content
    )
}

@Composable
fun BottomBar(appState: JetAppState) {
    NavigationSystem.Builder(NavigationType.BottomNavigation)
        .destinations(appState.topLevelDestinations) //mandatory
        .currentDestination(appState.currentDestination) //mandatory
        .modifier(Modifier)//init in the builder
        .alwaysShowIconLabel(false)//init in the builder
        .disableItems(DisableOption.None) //init in the builder
        .Build(onNavigate = appState::navigateToTopLevelDestination)
}

@Composable
fun NavRail(appState: JetAppState) {
    NavigationSystem.Builder(NavigationType.NavigationRail)
        .destinations(appState.topLevelDestinations)
        .currentDestination(appState.currentDestination)
        .modifier(Modifier)
        .alwaysShowIconLabel(false)
        .disableItems(DisableOption.None)
        .Build(onNavigate = appState::navigateToTopLevelDestination)
}

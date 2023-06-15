package com.example.composeexpert.coreUi.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.composeexpert.coreUi.designSystem.component.JetBackGround
import com.example.composeexpert.coreUi.designSystem.component.JetGradientBackGround
import com.example.composeexpert.coreUi.designSystem.theme.GradientColors
import com.example.composeexpert.coreUi.designSystem.theme.LocalGradientColors
import com.example.composeexpert.coreUi.navigation.JetBottomBar
import com.example.composeexpert.coreUi.navigation.TopLevelDestination

@Composable
fun JetApp(
    appState: JetAppState = rememberJetAppState()
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

            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JetScaffold(appState: JetAppState, content: @Composable (PaddingValues) -> Unit) {
    Scaffold(
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        bottomBar = {
            JetBottomBar(
                destinations = appState.topLevelDestinations,
                onNavigate = appState::navigateToTopLevelDestination,
                currentDestination = appState.currentDestination)
        }
    ) {
        content(it)
    }
}
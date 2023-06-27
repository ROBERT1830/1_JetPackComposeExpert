package com.example.composeexpert.feature.common

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.example.composeexpert.coreUi.navigation.JetAppFeature
import com.example.composeexpert.coreUi.navigation.NavigationCommand
import com.example.composeexpert.coreUi.navigation.jetAppComposable
import com.example.composeexpert.feature.mainFeed.detail.ItemDetailsScreen

fun NavHostController.navigateToMainFeedDetails(itemId: String) =
    navigate(NavigationCommand.GoToDetail(JetAppFeature.MAIN).createRoute(itemId))

fun NavGraphBuilder.mainFeedDetailsScreen(
    onBackClick: () -> Unit
) {
    jetAppComposable(NavigationCommand.GoToDetail(JetAppFeature.MAIN)) {
        ItemDetailsScreen() //if I have a tollBar or something we can apply onBack
    }
}
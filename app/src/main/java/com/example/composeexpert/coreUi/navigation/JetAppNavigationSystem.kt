package com.example.composeexpert.coreUi.navigation

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.composeexpert.coreUi.ui.JetAppState
import com.example.composeexpert.feature.addX.addScreen
import com.example.composeexpert.feature.common.mainFeedDetailsScreen
import com.example.composeexpert.feature.common.navigateToMainFeedDetails
import com.example.composeexpert.feature.favorites.favoritesScreen
import com.example.composeexpert.feature.mainFeed.mainFeedGraph
import com.example.composeexpert.feature.settings.settingsScreen

const val TAG = "nav_system_dbg"
@Composable
fun JetNavigation(jetAppState: JetAppState) {
    NavHost(
        navController = jetAppState.navController,
        startDestination = JetAppFeature.MAIN.route
    ) {
        mainFeedGraph(
            onItemClick = { itemId ->
                jetAppState.navController.navigateToMainFeedDetails(itemId)
            },
            nestedGraphs = {
                mainFeedDetailsScreen(onBackClick = jetAppState.navController::popBackStack)
            }
        )
        favoritesScreen(jetAppState)
        settingsScreen(jetAppState)
        addScreen(jetAppState)
    }

}


sealed class NavigationCommand(
    val jetAppFeature: JetAppFeature,
    val subRoute: String = "main",
    val navArgs: List<NavArg> = emptyList()
) {
    data class GoToMain(val feature: JetAppFeature) : NavigationCommand(feature)
    data class GoToDetail(val feature: JetAppFeature) :
        NavigationCommand(feature, DETAIL_SUBROUTE, listOf(NavArg.ITEM_ID)) {
        fun createRoute(itemId: String) =
            "${jetAppFeature.route}/$subRoute/${Uri.encode(itemId)}"
    }


    val route = kotlin.run {
        Log.d(TAG, "-----> ${ "${jetAppFeature.route}/$subRoute"
            .plus(getMandatoryArguments())
            .plus(getOptionArguments())}")


        "${jetAppFeature.route}/$subRoute"
            .plus(getMandatoryArguments())
            .plus(getOptionArguments())
    }

    private fun getMandatoryArguments(): String =
        navArgs.filter { !it.optional }
            .joinToString("/") { "{${it.key}}" }

    private fun getOptionArguments(): String =
        navArgs.filter { it.optional }
            .joinToString("&") { "${it.key}={${it.key}}" }
            .let { if (it.isNotEmpty()) "?$it" else "" }


    val args = navArgs.map {
        navArgument(it.key) { it.navType }
    }

    companion object {
        const val DETAIL_SUBROUTE = "detail"
    }
}

class JetArguments(private val args: List<String>) {
    constructor(savedStateHandle: SavedStateHandle, argsIds: List<String>) : this(
        argsIds.map { Uri.decode(checkNotNull(savedStateHandle[it])) }
    )
}

fun NavGraphBuilder.jetAppComposable(
    navCommand: NavigationCommand,
    content: @Composable (NavBackStackEntry) -> Unit,
) {
    composable(
        route = navCommand.route,
        arguments = navCommand.args
    ) {
        content(it)
    }
}

enum class NavArg(
    val key: String,
    val navType: NavType<*>,
    val optional: Boolean,
) {
    ITEM_ID("itemId", NavType.StringType, true)
}



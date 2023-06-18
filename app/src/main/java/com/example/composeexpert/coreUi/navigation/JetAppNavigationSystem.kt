package com.example.composeexpert.coreUi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.composeexpert.coreUi.ui.JetAppState
import com.example.composeexpert.feature.addX.addNav
import com.example.composeexpert.feature.favorites.favoritesNav
import com.example.composeexpert.feature.mainFeed.mainFeedNav
import com.example.composeexpert.feature.settings.settingNav

@Composable
fun JetNavigation(jetAppState: JetAppState) {
    NavHost(
        navController = jetAppState.navController,
        startDestination = JetAppFeature.MAIN.route
    ) {
        mainFeedNav(jetAppState)
        favoritesNav(jetAppState)
        settingNav(jetAppState)
        addNav(jetAppState)
    }
    
}


sealed class NavigationCommand(
    val jetAppFeature: JetAppFeature,
    val subRoute: String = "main",
    val navArgs: List<NavArg> = emptyList()
) {
    data class GoToMain(val feature: JetAppFeature): NavigationCommand(feature)
    data class GoToDetail(val feature: JetAppFeature): NavigationCommand(feature, DETAIL_SUBROUTE, listOf(NavArg.ITEM_ID)) {
        fun createRoute(itemId: Int) = "${jetAppFeature.route}/$subRoute/$itemId"
    }

    val route = kotlin.run {
        val argKeys = navArgs.map { "{${it.key}}" }
        listOf(jetAppFeature.route, subRoute)
            .plus(argKeys)
            .joinToString("/")
    }

    val args = navArgs.map {
        navArgument(it.key) { it.navType }
    }

    companion object {
        const val DETAIL_SUBROUTE = "detail"
    }
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
    val navType: NavType<*>
){
    ITEM_ID("itemId", NavType.IntType)
}



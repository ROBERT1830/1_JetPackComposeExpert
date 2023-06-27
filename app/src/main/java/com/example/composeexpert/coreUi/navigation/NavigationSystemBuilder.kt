package com.example.composeexpert.coreUi.navigation

import android.graphics.Color
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import com.example.composeexpert.coreUi.util.isTopLevelDestinationInHierarchy

sealed class NavigationType {
    object BottomNavigation : NavigationType()
    object NavigationRail : NavigationType()
}

sealed class DisableOption {
    object None : DisableOption()
    data class Disable(val indexes: List<Int>) : DisableOption()
}

interface NavigationSystem {

    class Builder(private val type: NavigationType) {

        private var destinations: List<TopLevelDestination> = emptyList()
        private var currentDestination: NavDestination? = null
        private var modifier: Modifier = Modifier

        private var alwaysShowIconLabel: Boolean = false
        private var itemsDisabledIndexes: List<Int> = emptyList()


        fun destinations(destinations: List<TopLevelDestination>) = apply {
            this.destinations = destinations
        }

        fun currentDestination(currentDestination: NavDestination?) = apply {
            currentDestination?.let { this.currentDestination = currentDestination }
        }

        fun modifier(modifier: Modifier) = apply {
            this.modifier = modifier
        }

        fun alwaysShowIconLabel(alwaysShowIconLabel: Boolean) = apply {
            this.alwaysShowIconLabel = alwaysShowIconLabel
        }

        fun disableItems(option: DisableOption) = apply {
            when (option) {
                is DisableOption.None -> this.itemsDisabledIndexes = emptyList()
                is DisableOption.Disable -> {
                    this.itemsDisabledIndexes = option.indexes
                }
            }
        }


        @Composable
        fun Build(onNavigate: (TopLevelDestination) -> Unit) {
            when (type) {
                is NavigationType.BottomNavigation -> {
                    JetBottomBar(
                        destinations = destinations,
                        currentDestination = currentDestination,
                        onNavigate = onNavigate,
                        alwaysShowIconLabel = alwaysShowIconLabel,
                        itemsDisabledIndexes = itemsDisabledIndexes,
                    )
                }

                is NavigationType.NavigationRail -> {
                    JetNavigationRail(
                        destinations = destinations,
                        currentDestination = currentDestination,
                        onNavigate = onNavigate,
                        alwaysShowIconLabel = alwaysShowIconLabel,
                        itemsDisabledIndexes = itemsDisabledIndexes
                    )
                }
            }
        }


    }
}

@Composable
fun JetBottomBar(
    destinations: List<TopLevelDestination>,
    currentDestination: NavDestination?,
    onNavigate: (TopLevelDestination) -> Unit,
    alwaysShowIconLabel: Boolean,
    itemsDisabledIndexes: List<Int>,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier,
        containerColor = NavigationSystemDefaults.navigationContainerColor(),
        contentColor = NavigationSystemDefaults.navigationContentColor(),
        tonalElevation = 0.dp
    ) {
        destinations.forEachIndexed { index, destination ->
            val itemSelected =
                currentDestination.isTopLevelDestinationInHierarchy(destination)
            NavigationBarItem(
                selected = itemSelected,
                onClick = { onNavigate(destination) },
                icon = {
                    NavigationIconSelector(
                        itemSelected = itemSelected,
                        destination = destination
                    )
                },
                modifier = modifier,
                enabled = !itemsDisabledIndexes.contains(index),
                label = { Text(stringResource(destination.iconLabelId)) },
                alwaysShowLabel = alwaysShowIconLabel,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor =  NavigationSystemDefaults.navigationSelectedItemColor(),
                    unselectedIconColor = NavigationSystemDefaults.navigationContentColor(),
                    selectedTextColor = NavigationSystemDefaults.navigationSelectedItemColor(),
                    unselectedTextColor = NavigationSystemDefaults.navigationContentColor(),
                    indicatorColor = NavigationSystemDefaults.navigationIndicatorColor(),
                )
            )
        }
    }
}

@Composable
fun JetNavigationRail(
    destinations: List<TopLevelDestination>,
    currentDestination: NavDestination?,
    onNavigate: (TopLevelDestination) -> Unit,
    alwaysShowIconLabel: Boolean,
    itemsDisabledIndexes: List<Int>,
    modifier: Modifier = Modifier,
) {
    NavigationRail(
        modifier = modifier,
        containerColor = NavigationSystemDefaults.navigationContainerColor(),
        contentColor = NavigationSystemDefaults.navigationContentColor(),
    ) {
        destinations.forEachIndexed { index, destination ->
            val itemSelected =
                currentDestination.isTopLevelDestinationInHierarchy(destination)
            NavigationRailItem(
                selected = itemSelected,
                onClick = { onNavigate(destination) },
                icon = {
                    NavigationIconSelector(
                        itemSelected = itemSelected,
                        destination = destination
                    )
                },
                modifier = modifier,
                enabled = !itemsDisabledIndexes.contains(index),
                label = { Text(stringResource(destination.iconLabelId)) },
                alwaysShowLabel = alwaysShowIconLabel,
                colors = NavigationRailItemDefaults.colors(
                    selectedIconColor =  NavigationSystemDefaults.navigationContentColor(),
                    unselectedIconColor = NavigationSystemDefaults.navigationContentColor(),
                    selectedTextColor = NavigationSystemDefaults.navigationSelectedItemColor(),
                    unselectedTextColor = NavigationSystemDefaults.navigationContentColor(),
                    indicatorColor = NavigationSystemDefaults.navigationIndicatorColor(),
                )
            )
        }
    }
}

//init data class when builder is crated
data class DefColor(
    val myColor1: androidx.compose.ui.graphics.Color? = null,
    val myColor2: Color
)

//colors can be optional with default colors.

object NavigationSystemDefaults {

    @Composable
    fun navigationContainerColor() = MaterialTheme.colorScheme.surface

    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.onSurface

    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.primary

    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.primaryContainer
}

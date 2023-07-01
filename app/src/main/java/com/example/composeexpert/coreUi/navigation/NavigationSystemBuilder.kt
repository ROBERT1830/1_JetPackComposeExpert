package com.example.composeexpert.coreUi.navigation

import androidx.compose.ui.graphics.Color
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import com.example.composeexpert.coreUi.util.isTopLevelDestinationInHierarchy
import java.lang.IllegalStateException


sealed class NavigationType {
    object BottomNavigation : NavigationType()
    object NavigationRail : NavigationType()
}

sealed class DisableOption {
    object None : DisableOption()
    data class Disable(val indexes: List<Int>) : DisableOption()
}

data class NavigationSystemColors(
    val containerColor:  @Composable () -> Color =  { BuilderNavigationSystemDefaults.navigationContainerColor() },
    val contentColor: @Composable () -> Color =  { BuilderNavigationSystemDefaults.navigationContentColor() },
    val selectedIconColor: @Composable () -> Color = { BuilderNavigationSystemDefaults.navigationSelectedItemColor() },
    val unselectedIconColor: @Composable () -> Color = { BuilderNavigationSystemDefaults.navigationContentColor() },
    val selectedTextColor: @Composable () -> Color = { BuilderNavigationSystemDefaults.navigationSelectedItemColor() },
    val unselectedTextColor: @Composable () -> Color = { BuilderNavigationSystemDefaults.navigationContentColor() },
    val indicatorColor: @Composable () -> Color = { BuilderNavigationSystemDefaults.navigationIndicatorColor() }
)

interface NavigationSystem {

    class Builder(private val type: NavigationType) {

        private var destinations: List<TopLevelDestination>? = null
        private var currentDestination: NavDestination? = null
        private var modifier: Modifier = Modifier

        private var alwaysShowIconLabel: Boolean = false
        private var itemsDisabledIndexes: List<Int> = emptyList()

        private var navigationSystemColors : NavigationSystemColors = NavigationSystemColors()

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
                    this.itemsDisabledIndexes = option.indexes.ifEmpty { emptyList() }
                }
            }
        }

        fun setColors(newColors: NavigationSystemColors) = apply {
            navigationSystemColors = navigationSystemColors.copy(
                containerColor = newColors.containerColor,
                contentColor = newColors.contentColor,
                selectedIconColor = newColors.selectedIconColor,
                unselectedIconColor = newColors.unselectedIconColor,
                selectedTextColor = newColors.selectedTextColor,
                unselectedTextColor = newColors.unselectedTextColor,
                indicatorColor = newColors.indicatorColor
            )
        }

        @Composable
        fun Build(onNavigate: (TopLevelDestination) -> Unit) {

            val isFirstTimeUpdate = remember { mutableStateOf(true) }

            LaunchedEffect(true) { if (isFirstTimeUpdate.value) {
                isFirstTimeUpdate.value = false }
            }

            if ((destinations == null || currentDestination == null) && !isFirstTimeUpdate.value) {
                throw IllegalStateException("Builder destinations and currentDestination parameters are not set!")
            }
            when (type) {
                is NavigationType.BottomNavigation -> {
                    JetBottomBar(
                        navigationSystemColors = navigationSystemColors,
                        destinations = destinations ?: emptyList(),
                        currentDestination = currentDestination,
                        onNavigate = onNavigate,
                        alwaysShowIconLabel = alwaysShowIconLabel,
                        itemsDisabledIndexes = itemsDisabledIndexes,
                    )
                }

                is NavigationType.NavigationRail -> {
                    JetNavigationRail(
                        navigationSystemColors = navigationSystemColors,
                        destinations = destinations ?: emptyList(),
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
private fun JetBottomBar(
    navigationSystemColors: NavigationSystemColors,
    destinations: List<TopLevelDestination>,
    currentDestination: NavDestination?,
    onNavigate: (TopLevelDestination) -> Unit,
    alwaysShowIconLabel: Boolean,
    itemsDisabledIndexes: List<Int>,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier,
        containerColor = navigationSystemColors.containerColor(),
        contentColor = navigationSystemColors.contentColor(),
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
                    selectedIconColor = navigationSystemColors.selectedIconColor(),
                    unselectedIconColor = navigationSystemColors.unselectedIconColor(),
                    selectedTextColor = navigationSystemColors.selectedTextColor(),
                    unselectedTextColor = navigationSystemColors.unselectedTextColor(),
                    indicatorColor = navigationSystemColors.indicatorColor(),
                )
            )
        }
    }
}

@Composable
private fun JetNavigationRail(
    navigationSystemColors: NavigationSystemColors,
    destinations: List<TopLevelDestination>,
    currentDestination: NavDestination?,
    onNavigate: (TopLevelDestination) -> Unit,
    alwaysShowIconLabel: Boolean,
    itemsDisabledIndexes: List<Int>,
    modifier: Modifier = Modifier,
) {
    NavigationRail(
        modifier = modifier,
        containerColor = navigationSystemColors.containerColor(),
        contentColor = navigationSystemColors.contentColor(),
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
                    selectedIconColor = navigationSystemColors.selectedIconColor(),
                    unselectedIconColor = navigationSystemColors.unselectedIconColor(),
                    selectedTextColor = navigationSystemColors.selectedTextColor(),
                    unselectedTextColor = navigationSystemColors.unselectedTextColor(),
                    indicatorColor = navigationSystemColors.indicatorColor(),
                )
            )
        }
    }
}

//colors can be optional with default colors.

private object BuilderNavigationSystemDefaults {

    @Composable
    fun navigationContainerColor() = MaterialTheme.colorScheme.secondary

    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.primary

    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.primary

    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.tertiary
}


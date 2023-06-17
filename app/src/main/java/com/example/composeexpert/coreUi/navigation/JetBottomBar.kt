package com.example.composeexpert.coreUi.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.example.composeexpert.coreUi.designSystem.Icon

@Composable
fun JetBottomBar(
    destinations: List<TopLevelDestination>,
    onNavigate: (destination: TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    JetNavigationBar(modifier = modifier) {
        destinations.forEach { destination ->
            val itemSelected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            JetNavigationBarItem(
                selected = itemSelected,
                onClick = { onNavigate(destination) },
                icon = {
                    when (
                        val icon = if (itemSelected) destination.selectedIcon
                        else destination.unselectedIcon
                    ) {
                        is Icon.ImageVectorIcon -> Icon(
                            imageVector = icon.imageVector,
                            contentDescription = null, //change for asString() the resource
                        )

                        is Icon.DrawableResourceIcon -> Icon(
                            painter = painterResource(id = icon.id),
                            contentDescription = null
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun JetNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    NavigationBar(
        modifier = modifier,
        containerColor = JetNavigationDefaults.bottomNavigationContainerColor(),
        contentColor = JetNavigationDefaults.bottomNavigationContentColor(),
        tonalElevation = 0.dp,
        content = content
    )
}

@Composable
fun RowScope.JetNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    selectedIcon: @Composable () -> Unit = icon,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = JetNavigationDefaults.bottomNavigationSelectedItemColor(),
            unselectedIconColor = JetNavigationDefaults.bottomNavigationContentColor(),
            unselectedTextColor = JetNavigationDefaults.bottomNavigationContentColor(),
            indicatorColor = JetNavigationDefaults.bottomNavigationIndicatorColor(),
        )
    )
}


object JetNavigationDefaults {

    @Composable
    fun bottomNavigationContainerColor() = MaterialTheme.colorScheme.surface

    @Composable
    fun bottomNavigationContentColor() = MaterialTheme.colorScheme.onSurface

    @Composable
    fun bottomNavigationSelectedItemColor() = MaterialTheme.colorScheme.primary

    @Composable
    fun bottomNavigationIndicatorColor() = MaterialTheme.colorScheme.primaryContainer
}


private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.route, true) ?: false
    } ?: false
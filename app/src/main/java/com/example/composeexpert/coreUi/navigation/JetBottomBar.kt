package com.example.composeexpert.coreUi.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import com.example.composeexpert.coreUi.util.isTopLevelDestinationInHierarchy

@Composable
fun JetBottomBar2(
    destinations: List<TopLevelDestination>,
    onNavigate: (destination: TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
){
    JetNavigationBar(modifier = modifier) {
        destinations.forEach { destination ->
            val itemSelected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            JetNavigationBarItem(
                selected = itemSelected,
                onClick = { onNavigate(destination) },
                icon = {
                    NavigationIconSelector(
                        itemSelected = itemSelected,
                        destination = destination
                    )
                },
                label = { Text(stringResource(destination.iconLabelId)) },
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
        containerColor = JetBottomNavigationDefaults.bottomNavigationContainerColor(),
        contentColor = JetBottomNavigationDefaults.bottomNavigationContentColor(),
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
            selectedIconColor = JetBottomNavigationDefaults.bottomNavigationSelectedItemColor(),
            unselectedIconColor = JetBottomNavigationDefaults.bottomNavigationContentColor(),
            selectedTextColor = JetBottomNavigationDefaults.bottomNavigationSelectedItemColor(),
            unselectedTextColor = JetBottomNavigationDefaults.bottomNavigationContentColor(),
            indicatorColor = JetBottomNavigationDefaults.bottomNavigationIndicatorColor(),
        )
    )
}


object JetBottomNavigationDefaults {

    @Composable
    fun bottomNavigationContainerColor() = MaterialTheme.colorScheme.surface

    @Composable
    fun bottomNavigationContentColor() = MaterialTheme.colorScheme.onSurface

    @Composable
    fun bottomNavigationSelectedItemColor() = MaterialTheme.colorScheme.primary

    @Composable
    fun bottomNavigationIndicatorColor() = MaterialTheme.colorScheme.primaryContainer
}
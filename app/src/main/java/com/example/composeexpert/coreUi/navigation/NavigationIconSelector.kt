package com.example.composeexpert.coreUi.navigation

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.composeexpert.coreUi.designSystem.Icon

@Composable
fun NavigationIconSelector(itemSelected: Boolean, destination: TopLevelDestination) {
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
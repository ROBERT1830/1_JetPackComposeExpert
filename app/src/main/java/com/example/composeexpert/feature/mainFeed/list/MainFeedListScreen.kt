package com.example.composeexpert.feature.mainFeed.list

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun MainFeedScreen(onItemClick: (itemId: Int) -> Unit) {
    Text(text = "Main Feed Screen")
    Button(
        onClick = { onItemClick(1) }
    ) {
        Text(text = "Details'")
    }
}
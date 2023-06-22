package com.example.composeexpert

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.example.composeexpert.coreUi.designSystem.theme.ComposeExpertTheme
import com.example.composeexpert.coreUi.ui.JetApp

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeExpertTheme { JetApp(windowSizeClass = calculateWindowSizeClass(activity = this)) }
        }
    }
}

package com.example.composeexpert

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.composeexpert.coreUi.designSystem.theme.ComposeExpertTheme
import com.example.composeexpert.coreUi.ui.JetApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeExpertTheme { JetApp() }
        }
    }
}

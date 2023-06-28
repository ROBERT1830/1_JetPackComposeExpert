package com.example.composeexpert

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.example.composeexpert.core.network.INetworkMonitor
import com.example.composeexpert.coreUi.designSystem.theme.ComposeExpertTheme
import com.example.composeexpert.coreUi.ui.JetApp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkMonitor: INetworkMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeExpertTheme { JetApp(
                networkMonitor = networkMonitor,
                windowSizeClass = calculateWindowSizeClass(activity = this)
            ) }
        }
    }
}

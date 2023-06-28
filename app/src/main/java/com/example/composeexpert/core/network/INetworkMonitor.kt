package com.example.composeexpert.core.network

import kotlinx.coroutines.flow.Flow

interface INetworkMonitor {
    val isDeviceOnline: Flow<Boolean>
}
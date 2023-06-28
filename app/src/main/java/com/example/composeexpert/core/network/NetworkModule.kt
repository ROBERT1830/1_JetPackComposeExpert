package com.example.composeexpert.core.network

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {
    @Binds
    fun bindsNetworkMonitor(networkMonitor: NetworkMonitorlmpl): INetworkMonitor
}
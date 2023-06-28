package com.example.composeexpert.core.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import androidx.core.content.getSystemService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import javax.inject.Inject

class NetworkMonitorlmpl @Inject constructor(
    @ApplicationContext private val context: Context
) : INetworkMonitor {
    override val isDeviceOnline: Flow<Boolean> = callbackFlow {
        //getConnectivityManager using context
        val connectivityManager = context.getSystemService<ConnectivityManager>()
        //if service is not available then send false and close channel
        if (connectivityManager == null) {
            trySend(false)
            close()
            return@callbackFlow
        }
        //if service is available create networkCallBack
        val networkCallback = object : NetworkCallback() {
            //set of networks that will have different types of network connection
            val setOfNetworks = mutableSetOf<Network>()

            //if one network connection type is available we include it into the set and send true
            override fun onAvailable(network: Network) {
                setOfNetworks += network
                trySend(true)
            }

            //if some type of network has been lost then we remove it from the set and send setOfNetworks.isNotEmpty().
            //we will send false when there are no more remaining network in the set. If still there are network objects
            //in the set then means that device is still connected to at least one network
            override fun onLost(network: Network) {
                setOfNetworks -= network
                trySend(setOfNetworks.isNotEmpty())
            }
        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()

        connectivityManager.registerNetworkCallback(request, networkCallback)
        //initial connectivity status to be sent/
        //By removing this line, the initial connectivity status will not be emitted
        // immediately when the isOnline flow is collected
        trySend(connectivityManager.isDeviceCurrentlyConnected())

        awaitClose {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        }
    }.conflate() //if there are multiple emissions in short time, the subscribers only will receive las one


    private fun ConnectivityManager.isDeviceCurrentlyConnected() = when {
        VERSION.SDK_INT >= VERSION_CODES.M ->
            activeNetwork?.let { getNetworkCapabilities(it) }
                ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        else -> activeNetworkInfo?.isConnected
    } ?: false
}
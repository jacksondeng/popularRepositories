package com.jacksondeng.gojek.popularrepositories.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkRequest
import android.os.Build
import android.widget.TextView
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.security.ProviderInstaller
import kotlin.contracts.ContractBuilder
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract


fun TextView.leftDrawable(@DrawableRes id: Int = 0, @DimenRes sizeRes: Int) {
    val drawable = ContextCompat.getDrawable(context, id)
    val size = resources.getDimensionPixelSize(sizeRes)
    drawable?.setBounds(0, 0, size, size)
    this.setCompoundDrawables(drawable, null, null, null)
}

fun preLollipop(block: () -> Unit) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
        block.invoke()
    }
}

fun postLollipop(block: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        block.invoke()
    }
}

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun Context.registerNetworkCallback(netWorkCallback: ConnectivityManager.NetworkCallback) {
    val connectivityManger =
        this.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    connectivityManger.registerNetworkCallback(
        NetworkRequest.Builder().build(),
        netWorkCallback
    )
}

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun Context.unregisterNetworkCallback(netWorkCallback: ConnectivityManager.NetworkCallback) {
    val connectivityManger =
        this.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    connectivityManger.unregisterNetworkCallback(netWorkCallback)
}


@Suppress("DEPRECATION")
fun Context.isOnline(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    return if (connectivityManager != null && connectivityManager.activeNetworkInfo != null)
        connectivityManager.activeNetworkInfo!!.isConnected
    else
        false
}

fun Context.installTls12IfNeeded(onErrorAction: () -> Unit) {
    try {
        ProviderInstaller.installIfNeeded(this)
    } catch (e: GooglePlayServicesRepairableException) {
        // Prompt the user to install/update/enable Google Play services.
        GoogleApiAvailability.getInstance().showErrorNotification(this, e.connectionStatusCode)
    } catch (e: GooglePlayServicesNotAvailableException) {
        onErrorAction.invoke()
    }
}
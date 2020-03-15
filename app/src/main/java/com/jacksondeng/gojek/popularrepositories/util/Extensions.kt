package com.jacksondeng.gojek.popularrepositories.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import android.widget.TextView
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat


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
fun Context.registerNetworkCallback(onAvailableAction: () -> Unit, onLostAction: () -> Unit) {
    val connectivityManger =
        this.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    connectivityManger.registerNetworkCallback(
        NetworkRequest.Builder().build(),
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                onAvailableAction.invoke()
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                onLostAction.invoke()
            }
        }
    )
}
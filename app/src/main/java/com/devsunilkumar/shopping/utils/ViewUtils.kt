package com.devsunilkumar.shopping.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.Settings
import android.view.View
import android.widget.ProgressBar
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.devsunilkumar.shopping.R
import com.google.android.material.snackbar.Snackbar


fun View.snackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->
        snackbar.setAction("Ok") {
            snackbar.dismiss()
        }
    }.show()
}


 fun isInternetAvailable(applicationContext : Context): Boolean {
    var result = false
    val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    connectivityManager?.let { cm ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }
        } else {
            connectivityManager.activeNetworkInfo.also {
                result = it != null && it.isConnected
            }
        }
    }
    return result
}

fun noInternetPopup(ctx:Context){
    val dialog = AlertDialog.Builder(ctx)
    dialog.setTitle("Error")
    dialog.setMessage("Internet Connection not Found")
    dialog.setPositiveButton("Open Settings") { text, listener ->
        val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
        ctx.startActivity(settingsIntent)

    }
    dialog.setNegativeButton("Exit") { text, listener ->

        ActivityCompat.finishAffinity(ctx as Activity)
    }
    dialog.create()
    dialog.show()


}



















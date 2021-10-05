package com.avalon.calizer.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Network @Inject constructor(val context: Context):NetworkConnectivity  {
    override fun getNetworkInfo(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
       return checkConnectionType(cm)
    }

    override fun isConnected(): Boolean {
        return getNetworkInfo()
    }

    private fun checkConnectionType(cm: ConnectivityManager):Boolean{
       return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            cm.getNetworkCapabilities(cm.activeNetwork)?.let { itNc->
                itNc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || itNc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)|| itNc.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
            }?:false
        }else{
            cm.activeNetworkInfo?.let { itNi->
                itNi.type ==ConnectivityManager.TYPE_WIFI || itNi.type == ConnectivityManager.TYPE_MOBILE || itNi.type == ConnectivityManager.TYPE_VPN
            }?:false
       }
    }

}

interface NetworkConnectivity{
    fun getNetworkInfo():Boolean
    fun isConnected():Boolean
}
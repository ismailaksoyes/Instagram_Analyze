package com.avalon.calizer.utils

import android.os.NetworkOnMainThreadException
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

class CheckInternetConnection {

    fun isAvailableInternet():Boolean{
      return  try {
          val timeOut = 1500
          val socket = Socket()
          val socketAddress = InetSocketAddress("8.8.8.8",53)
          socket.connect(socketAddress,timeOut)
          socket.close()
          true
        }catch (e:NetworkOnMainThreadException){
            false
        }


    }
}
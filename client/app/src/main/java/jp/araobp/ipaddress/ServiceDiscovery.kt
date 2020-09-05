package jp.araobp.ipaddress

import android.content.Context
import android.net.wifi.WifiManager
import android.util.Log
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.SocketTimeoutException
import kotlin.concurrent.thread

class ServiceDiscovery(val context: Context) {

    companion object {
        const val TAG = "ServiceDiscovery"
        const val DISCOVERY_PORT = 18084
    }

    interface IDiscoveryCallback {
        fun onResult(err: Boolean, ipAddress: String, serviceId: String)
    }

    fun discover(callback: IDiscoveryCallback) {
        thread {
            var serviceId = ""
            var ipAddress = ""
            var err = true

            val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val multicastLock = wifiManager.createMulticastLock(TAG)
            multicastLock.setReferenceCounted(true)
            if (multicastLock != null && !multicastLock.isHeld) {
                multicastLock.acquire()
                val buf = ByteArray(6)
                val dp = DatagramPacket(buf, buf.size)
                val datagramSocket = DatagramSocket(DISCOVERY_PORT).apply {
                    broadcast = true
                    soTimeout = 5000  // 5 sec timeout
                }
                try {
                    datagramSocket.receive(dp)
                    datagramSocket.close()
                    serviceId = String(buf, 0, buf.size)
                    ipAddress = dp.address.hostAddress
                    Log.d(TAG, "Raspberry Pi discovered at $ipAddress")
                    err = false
                } catch (e: SocketTimeoutException) {
                    datagramSocket.close()
                    Log.d(TAG, "Raspberry Pi discovery timeout")
                } finally {
                    multicastLock.release()
                }
            } else {
                Log.d(TAG, "Multicast lock acquisition failed")
            }

            callback.onResult(err, ipAddress, serviceId)
        }
    }
}
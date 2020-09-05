package jp.araobp.ipaddress

import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.math.BigInteger
import java.net.InetAddress

class MainActivity : AppCompatActivity() {

    private lateinit var mDiscovery: ServiceDiscovery

    // [Reference] https://stackoverflow.com/questions/20846120/alternative-for-formatter-formatipaddressint
    fun ipAddress(): String {
        val wifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        val wifiinfo = wifiManager.connectionInfo
        val ipAddress = BigInteger.valueOf(wifiinfo.ipAddress.toLong()).toByteArray()
        return InetAddress.getByAddress(ipAddress.reversedArray()).hostAddress
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDiscovery = ServiceDiscovery(this)
    }

    override fun onResume() {
        super.onResume()

        textViewAndroid.text = ipAddress()

        mDiscovery.discover(
            object: ServiceDiscovery.IDiscoveryCallback {
                override fun onResult(err: Boolean, ipAddress: String, serviceId: String) {
                    textViewRaspberryPi.text = ipAddress
                    textViewServiceId.text = serviceId
                }
            }
        )

    }

}
package com.rotative_table_2019.ui.main

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.widget.Toast

class Bluetooh  {
    private val enableBtIntent = Intent()
    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action: String? = intent.action
            when(action) {
                BluetoothDevice.ACTION_FOUND -> {
                    // Discovery has found a device. Get the BluetoothDevice
                    // object and its info from the Intent.
                    val device: BluetoothDevice =
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    val deviceName = device.name
                    val deviceHardwareAddress = device.address // MAC address
                }
            }
        }
    }

      init {
          val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
          if (bluetoothAdapter == null) {
              fun Context.toast(message: CharSequence) =
                  Toast.makeText(this, "Jean Active le Bluetooch", Toast.LENGTH_SHORT).show()
          }
          if (bluetoothAdapter?.isEnabled == false) {
                enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)

              //startActivityForResult(enableBtIntent, 1)
          }
          val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
          //registerReceiver(receiver, filter)
      }


}
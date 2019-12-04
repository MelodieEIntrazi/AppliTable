package com.rotative_table_2019

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

import com.rotative_table_2019.ui.main.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*

class SelectDeviceActivity : AppCompatActivity() {

    private var bluetoothAdapter: BluetoothAdapter? = null
    private lateinit var pairedDevices: Set<BluetoothDevice>
    private val REQUEST_ENABLE_BLUETOOTH = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = this.findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = findViewById(R.id.fab)


        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if(bluetoothAdapter?.isEnabled == true){
            fab.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.design_default_color_primary_dark))
        }

        fab.setOnClickListener { view ->
            if (bluetoothAdapter == null) {
                Snackbar.make(view, "Problem with bluetooth or Unsupported", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
            else if (!bluetoothAdapter!!.isEnabled ) {
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BLUETOOTH)
                Snackbar.make(view, "Bluetooth activating", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
                fab.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.design_default_color_primary_dark))


            }else {
                Snackbar.make(view, "Bluetooth already enable", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }


        }
        select_device_refresh.setOnClickListener{
            pairedDeviceList()}


    }
    companion object{
        val EXTRA_ADDRESS: String = "Device_address"
    }
    private fun pairedDeviceList() {
        pairedDevices = bluetoothAdapter!!.bondedDevices
        val list : ArrayList<BluetoothDevice> = ArrayList()
        if(!pairedDevices.isEmpty()){
            for (device: BluetoothDevice in pairedDevices){
                list.add(device)
                Log.i("device",""+device)
            }
        }else{
            Snackbar.make(fab,"No paired Devices Found",Snackbar.LENGTH_LONG).setAction("Action", null).show()
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,list)
        select_device_list.adapter = adapter
        select_device_list.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val device:BluetoothDevice=list[position]
            val address:String = device.address
            val intent = Intent(this,ControlActivity::class.java)
            intent.putExtra(EXTRA_ADDRESS,address)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ENABLE_BLUETOOTH){
            if(resultCode== Activity.RESULT_OK){
                if (bluetoothAdapter!!.isEnabled){Snackbar.make(fab,"Bluetooth has been enable",Snackbar.LENGTH_LONG).setAction("Action", null).show()

                }else{
                    Snackbar.make(fab,"Bluetooth has been disabled",Snackbar.LENGTH_LONG).setAction("Action", null).show()
                }
            }else if (resultCode == Activity.RESULT_CANCELED){
                Snackbar.make(fab,"Bluetooth has been canceled",Snackbar.LENGTH_LONG).setAction("Action", null).show()
            }
        }
    }
}
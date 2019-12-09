package com.example.application.Activité_n1


import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.application.Activité_n1.Bluetooth.Peripherique
import com.example.application.Activité_n1.Bluetooth.PeripheriqueAdapter
import com.example.application.Activité_n1.RecyclerView.RecyclerTouch
import com.example.application.Activité_n2.MainActivity
import com.example.application.R
import java.util.*

/*
Activité 1 ayant pour but de choisir le bon périphérique parmi une liste de périphériques connus appareillé en bluetooth
Lorsque l'on click sur le bon périphérique, il s'affiche au dessus du bouton 'connecter' et on peut appuyer ainsi sur ce bouton pour
aller à l'activité suivante

 */

class Connexion : AppCompatActivity() {
    private var devices: Set<BluetoothDevice>? = null
    private var adaptateurBluetooth: BluetoothAdapter? = null
    private val bluetoothReceiver: BroadcastReceiver? = null
    private var peripheriques: ArrayList<Peripherique>? = null
    private var peripherique: Peripherique? = null
    private var noms: ArrayList<String>? = null
    private val handler: Handler? = null

    private var btnConnecter: Button? = null
    private var listePeripheriques: androidx.recyclerview.widget.RecyclerView? = null

    private var peripheriqueText: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.connexion)
        context = applicationContext

        peripheriqueText = findViewById(R.id.textPeripheriqueName)
        btnConnecter = findViewById(R.id.btnConnecter)

        btnConnecter!!.setOnClickListener {
            System.out.println("click sur connecter")
            peripherique!!.connecter()
            while (!peripherique!!.isConnected);
            Peripherique.peripherique = peripherique
            val intent = Intent(this@Connexion, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        adaptateurBluetooth = BluetoothAdapter.getDefaultAdapter()
        if (adaptateurBluetooth == null) {
            Toast.makeText(applicationContext, "Bluetooth non activé !", Toast.LENGTH_SHORT).show()
        } else {
            if (!adaptateurBluetooth!!.isEnabled) {
                Toast.makeText(applicationContext, "Bluetooth non activé !", Toast.LENGTH_SHORT).show()
                val activeBlueTooth = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(activeBlueTooth, REQUEST_CODE_ENABLE_BLUETOOTH)
                //bluetoothAdapter.enable();
            } else {
                Toast.makeText(applicationContext, "Bluetooth activé", Toast.LENGTH_SHORT).show()

                // Recherche des périphériques connus
                peripheriques = ArrayList()
                noms = ArrayList()
                devices = adaptateurBluetooth!!.bondedDevices
                for (blueDevice in devices!!) {
                    //Toast.makeText(getApplicationContext(), "Périphérique = " + blueDevice.getName(), Toast.LENGTH_SHORT).show();
                    peripheriques!!.add(Peripherique(blueDevice, handler))
                    noms!!.add(blueDevice.name)
                }

                if (peripheriques!!.size == 0)
                    peripheriques!!.add(Peripherique(null, handler))
                if (noms!!.size == 0)
                    noms!!.add("Aucun")


                println(peripheriques!!.size)

                listePeripheriques = findViewById<View>(R.id.bluetoothList) as androidx.recyclerview.widget.RecyclerView
                val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
                listePeripheriques!!.layoutManager = layoutManager
                listePeripheriques!!.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
                val peripheriqueAdapter = PeripheriqueAdapter(this, peripheriques!!)
                listePeripheriques!!.adapter = peripheriqueAdapter

                listePeripheriques!!.addOnItemTouchListener(RecyclerTouch(this, listePeripheriques!!, object : RecyclerTouch.ClickListener {
                    override fun onClick(view: View?, position: Int) {
                        peripherique = peripheriques!![position]
                        peripheriqueText!!.text = peripherique!!.nom
                    }

                    override fun onLongClick(view: View?, position: Int) {
                        peripherique = peripheriques!![position]
                        peripheriqueText!!.text = peripherique!!.nom
                    }

                }))
            }
        }
    }

    /*
    Cette fonction permet d'afficher une notification lorsque le bluetooth du téléphone est activé ou non
     */

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode != REQUEST_CODE_ENABLE_BLUETOOTH)
            return
        if (resultCode == Activity.RESULT_OK) {
            Toast.makeText(context, "Bluetooth activé", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Bluetooth non activé !", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        if (adaptateurBluetooth != null) {
            adaptateurBluetooth!!.cancelDiscovery()
        }
        if (bluetoothReceiver != null) {
            unregisterReceiver(bluetoothReceiver)
        }
        super.onDestroy()

    }

    companion object {
        private val REQUEST_CODE_ENABLE_BLUETOOTH = 0
        private var context: Context? = null
    }
}


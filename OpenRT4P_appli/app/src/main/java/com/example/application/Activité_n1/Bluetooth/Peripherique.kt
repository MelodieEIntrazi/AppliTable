package com.example.application.Activité_n1.Bluetooth

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import com.example.application.Activité_n2.Fragments.Menu.Menu
import com.example.application.Activité_n2.Instructions.InstructionCamera
import com.example.application.Activité_n2.Instructions.InstructionMoteur
import com.example.application.Activité_n2.MainActivity
import com.example.application.Activité_n2.Order.ListOrder
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*

/*
la class périphérique sert de communication entre le téléphone et le boitier de commande
Plusieurs fonctions sont présentes comme :
'envoyer' qui permet d'envoyer des informations du téléphone vers le boitier de commande
'decode' qui permet de decoder les informations reçues du boitier et un possible affichage sur le téléphone
connecter et decconecter sont appeler lors de la connexion au boitier de commande :
    avant chaque connexion, une deconnexion de sécurité est appelé puis la connexion avec le boitier de commande se fait
 */
class Peripherique(device: BluetoothDevice?, handler: Handler?) {
    var nom: String? = null
    var adresse: String? = null
    private var handler: Handler? = null
    private var device: BluetoothDevice? = null
    private var socket: BluetoothSocket? = null
    private var receiveStream: InputStream? = null
    private var sendStream: OutputStream? = null
    private var tReception: TReception? = null
    var isConnected = false

    override fun toString(): String {
        return "\nNom : $nom\nAdresse : $adresse"
    }

    fun envoyer(data: String) {
        try {
            sendStream!!.write(data.toByteArray())
            sendStream!!.flush()

        } catch (e: IOException) {
            println("<Socket> error send")
            e.printStackTrace()
        }
    }

    fun connecter() {
        println("Connecter")
        object : Thread() {
            override fun run() {
                deconnecter()
                println("connexion en cours")
                try {
                    try {
                        socket = device!!.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"))
                        tReception = TReception(handler)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        return
                    }
                    socket!!.connect()
                    sendStream = socket!!.outputStream
                    receiveStream = socket!!.inputStream
                    tReception!!.start()
                    isConnected = true
                    println("connexion établie")
                } catch (e: IOException) {
                    println("<Socket> error connect")
                    e.printStackTrace()
                }
            }
        }.start()
    }

    fun deconnecter(): Boolean {
        println("déconnexion")
        isConnected = false
        try {
            tReception!!.arreter()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (receiveStream != null) {
            try {
                receiveStream!!.close()
            } catch (e: Exception) {
            }
            receiveStream = null
        }
        if (sendStream != null) {
            try {
                sendStream!!.close()
            } catch (e: Exception) {
            }
            sendStream = null
        }
        if (socket != null) {
            try {
                socket!!.close()
            } catch (e: Exception) {
                println("ERROR " + e.message.toString())
            }
            //socket = null
        }
        println("déconnexion réussie")
        return true
    }

    private inner class TReception internal constructor(var handlerUI: Handler?) : Thread() {
        private var fini = false
        override fun run() {
            while (!fini) {
                try {
                    if (receiveStream!!.available() > 0) {
                        val buffer = ByteArray(100)
                        val k = receiveStream!!.read(buffer, 0, 100)
                        if (k > 0) {
                            val rawdata = ByteArray(k)
                            for (i in 0 until k) rawdata[i] = buffer[i]
                            val data = String(rawdata)
                            decode(data)
                        }
                    }
                    /*try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                } catch (e: IOException) { //System.out.println("<Socket> error read");
                    e.printStackTrace()
                }
            }
        }

        fun arreter() {
            if (!fini) {
                fini = true
            }
            try {
                interrupt()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }

        fun decode(data: String) {
            val tableauDonnees = data.split(",").toTypedArray()
            println(data)
            if (tableauDonnees.isEmpty()) return
            if (tableauDonnees[0] == "fini") {
                handlerUI = Handler(Looper.getMainLooper())
                handlerUI!!.post { Menu.pauseButton!!.text = "PAUSE" }
                Menu.instructionAdapter!!.instructionList = null
                handlerUI = Handler(Looper.getMainLooper())
                handlerUI!!.post {
                    ListOrder.delete(tableauDonnees[1].toInt())
                    Menu.view!!.visibility = View.INVISIBLE
                    Menu.listInfos!!.visibility = View.INVISIBLE
                    Menu.deleteButton!!.visibility = View.INVISIBLE
                }
            } else if (tableauDonnees[0] == "creation") {
                val idCommande = tableauDonnees[1].toInt()
                val idInstruction = tableauDonnees[2].toInt()
                if (tableauDonnees[3] == "moteur") {
                    val acceleration = tableauDonnees[4].toInt()
                    val vitesse = tableauDonnees[5].toInt()
                    val direction = tableauDonnees[6].toInt()
                    val choixRotation = tableauDonnees[7].toInt()
                    val stepsTime = tableauDonnees[8].toInt()
                    val instructionMoteur = InstructionMoteur(idCommande, idInstruction, acceleration,
                            vitesse, direction, choixRotation, stepsTime)
                    ListOrder.getById(idCommande)?.listInstruction!!.add(instructionMoteur)
                } else if (tableauDonnees[3].contains("camera")) {
                    val pause = tableauDonnees[5].toInt()
                    val nombre_de_photos = tableauDonnees[6].toInt()
                    val instructionCamera = InstructionCamera(idCommande, idInstruction, nombre_de_photos, pause)
                    ListOrder.getById(idCommande)?.listInstruction!!.add(instructionCamera)
                }
                handlerUI = Handler(Looper.getMainLooper())
                handlerUI!!.post { Menu.instructionAdapter!!.notifyDataSetChanged() }
            } else if (tableauDonnees[0] == "en cours") {
                val idCommande = tableauDonnees[1].toInt()
                val idInstruction = tableauDonnees[2].toInt()
                if (idCommande == -1) {
                } else {
                    if (idInstruction < ListOrder.getById(idCommande)?.listInstruction!!.size) {
                        if (idCommande >= 1) {
                            if (ListOrder.getById(idCommande)?.listInstruction!!.size == 1) {
                                ListOrder.getById(idCommande)?.listInstruction!![idInstruction - 1].termine = 1
                            } else if (idInstruction > 1) {
                                ListOrder.getById(idCommande)?.listInstruction!![idInstruction - 2].termine = 2
                                ListOrder.getById(idCommande)?.listInstruction!![idInstruction - 1].termine = 1
                            }
                        }
                    }
                }
                handlerUI = Handler(Looper.getMainLooper())
                handlerUI!!.post {
                    Menu.pauseButton!!.text = "PAUSE"
                    Menu.instructionAdapter!!.notifyDataSetChanged()
                }
            } else if (tableauDonnees[0] == "connexion") {
                handlerUI = Handler(Looper.getMainLooper())
                handlerUI!!.post { Toast.makeText(MainActivity.context!!, "CONNEXION DES PERIPHERIQUES : SUCCESS", Toast.LENGTH_LONG).show() }
            }
        }

    }

    companion object {
        var peripherique: Peripherique? = null
    }

    init {
        if (device != null) {
            this.device = device
            nom = device.name
            adresse = device.address
            this.handler = handler
        } else {
            this.device = device
            nom = "Aucun"
            adresse = ""
            this.handler = handler
        }
        try {
            this.socket = device!!.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"))
            receiveStream = socket!!.inputStream
            sendStream = socket!!.outputStream
        } catch (e: IOException) {
            e.printStackTrace()
            socket = null
        }
        if (socket != null) {
            tReception = TReception(handler)
        }

    }
}
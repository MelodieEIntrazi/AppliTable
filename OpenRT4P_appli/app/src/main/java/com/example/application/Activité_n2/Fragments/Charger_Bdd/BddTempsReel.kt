package com.example.application.Activité_n2.Fragments.Charger_Bdd

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import com.example.application.Activité_n1.Bluetooth.Peripherique.Companion.peripherique
import com.example.application.Activité_n2.Adapter.ValeurReelAdapter
import com.example.application.Activité_n2.Fragments.Temps_réel.TempsReel
import com.example.application.Activité_n2.Interface.ChangeFragments
import com.example.application.Activité_n2.Interface.SelectionReel
import com.example.application.Activité_n2.MainActivity
import com.example.application.Activité_n2.Order.ListOrder
import com.example.application.Activité_n2.Order.TempsReelOrder
import com.example.application.BDD.DbThread
import com.example.application.BDD.ValeurReelAndProgDataBase
import com.example.application.Objets.ValeurReel
import com.example.application.R
import kotlinx.coroutines.Runnable

/**
 * Permet de charger des informations gardées en mémoire lors d'une ancienne sauvegarde
 */
class BddTempsReel : androidx.fragment.app.Fragment(), SelectionReel {
    // private var mBDDAsyncTask: ChargementBDDVR? = null
    private lateinit var mListView: ListView
    private var adapter: ValeurReelAdapter? = null
    private lateinit var mDbThread: DbThread
    private val mUiHandler = Handler()
    private val changeListener: ChangeFragments = MainActivity.listener!!
    private var valeurReelAndProgDataBase: ValeurReelAndProgDataBase? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? { // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bdd_temps_reel, container, false)
        mListView = view.findViewById(R.id.ListeViewReel)
        mDbThread = DbThread("dbThread")
        mDbThread.start()
        valeurReelAndProgDataBase = ValeurReelAndProgDataBase.getDatabase(MainActivity.context!!)
        fetchWeatherDataFromDb()


        return view
    }


    /*
    Permet de selectionner les informations concernant le mode Temps réel pour les réutiliser lors du mode Temps réel
     */
    override fun onSelection(valeurR: ValeurReel?) {
        //val transaction = fragmentManager!!.beginTransaction()
        /* val fragment = TempsReel()
         val bundle = Bundle()
         bundle.putString("vitesse", valeurR!!.speed)
         bundle.putString("acceleration", valeurR.acceleration)
         bundle.putString("rotationNumber", valeurR.rotationNumber)
         bundle.putBoolean("rotationMode", valeurR.rotationMode!!)
         bundle.putBoolean("direction", valeurR.direction!!)
         bundle.putString("tableSteps", valeurR.tableSteps)
         fragment.arguments = bundle
         changeListener.onChangeFragment(fragment)*/
        val tempsReelOrder = TempsReelOrder(valeurR!!.acceleration!!.toInt(), valeurR.speed!!.toInt(),
                valeurR.direction!!, valeurR.tableSteps!!.toInt(), valeurR.rotationMode!!, valeurR.rotationNumber!!.toInt())
        ListOrder.list.add(tempsReelOrder)
        com.example.application.Activité_n2.Fragments.Menu.Menu.orderAdapter!!.notifyDataSetChanged()
        var data = ""
        data += tempsReelOrder.id.toString() + ","
        data += "1" + ","
        data += valeurR.acceleration + ","
        data += valeurR.speed + ","
        data += valeurR.tableSteps + ","
        data += if (valeurR.direction == true) {
            "1" + "," // Time mode
        } else {
            "0" + "," // turn mode
        }
        data += if (valeurR.rotationMode == true) {
            "1" + ","
        } else {
            "0" + ","
        }
        data += valeurR.rotationNumber + ","
        data += "-1" + ","
        data += "-1" + ","
        data += "-1" + ","
        data += "-1"
        println(data)
        peripherique!!.envoyer(data)
        changeListener.onChangeFragment(com.example.application.Activité_n2.Fragments.Menu.Menu.menu)
        //transaction.replace(R.id.fragment, fragment).addToBackStack(null).commit()
    }

    /*
    Est appelé lors du click sur le bouton delete present sur le fragment et permet ainsi de revenir sur le mode temps réel
     */
    override fun onDelete(valeurR: ValeurReel) {
        val task = Runnable {
            valeurReelAndProgDataBase?.vRDao()?.delete(valeurR)
        }
        mDbThread.postTask(task)
        //fragmentManager!!.beginTransaction().replace(R.id.fragment, TempsReel.temps_reel).addToBackStack(null).commit()
        changeListener.onChangeFragment(TempsReel.temps_reel)
    }

    companion object {
        @JvmField
        var bddTempsReel = BddTempsReel()
    }

    private fun fetchWeatherDataFromDb() {
        val task = Runnable {
            val valeurReelData = valeurReelAndProgDataBase?.vRDao()?.getAll()
            mUiHandler.post {
                if (valeurReelData == null || valeurReelData.isEmpty()) {
                    Toast.makeText(MainActivity.context!!, "Rien dans la BDD", Toast.LENGTH_SHORT).show()
                    changeListener.onChangeFragment(TempsReel.temps_reel)
                } else {
                    adapter = ValeurReelAdapter(valeurReelData)
                    mListView.adapter = adapter
                    adapter!!.setmListener(this)
                    mListView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id -> Toast.makeText(context, position, Toast.LENGTH_SHORT).show() }
                }
            }
        }
        mDbThread.postTask(task)
    }

    override fun onDestroy() {
        mDbThread.quit()
        super.onDestroy()
    }

}
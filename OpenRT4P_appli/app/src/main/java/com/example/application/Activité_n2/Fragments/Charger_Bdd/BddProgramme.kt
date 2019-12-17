package com.example.application.Activité_n2.Fragments.Charger_Bdd

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import android.widget.Toast
import com.example.application.Activité_n1.Bluetooth.Peripherique.Companion.peripherique
import com.example.application.Activité_n2.Adapter.ValeurProgrammeAdapter
import com.example.application.Activité_n2.Fragments.Focus.FocusParametre
import com.example.application.Activité_n2.Fragments.Menu.Menu
import com.example.application.Activité_n2.Interface.ChangeFragments
import com.example.application.Activité_n2.Interface.SelectionProgramme
import com.example.application.Activité_n2.MainActivity
import com.example.application.Activité_n2.Order.ListOrder
import com.example.application.Activité_n2.Order.ProgrammeOrder
import com.example.application.BDD.DbThread
import com.example.application.BDD.ValeurReelAndProgDataBase
import com.example.application.Objets.ValeurProgramme
import com.example.application.R

/**
 * Permet de charger des informations gardées en mémoire lors d'une ancienne sauvegarde
 */
class BddProgramme : androidx.fragment.app.Fragment(), SelectionProgramme {
    private lateinit var mListView: ListView
    private var adapter: ValeurProgrammeAdapter? = null
    private lateinit var mDbThread: DbThread
    private val mUiHandler = Handler()
    private var valeurReelAndProgDataBase: ValeurReelAndProgDataBase? = null
    private val changeListener: ChangeFragments = MainActivity.listener!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? { // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bdd_programme, container, false)
        mListView = view.findViewById(R.id.ListeViewProgramme)
        mDbThread = DbThread("dbProgThread")
        mDbThread.start()
        valeurReelAndProgDataBase = ValeurReelAndProgDataBase.getDatabase(MainActivity.context!!)
        fetchWeatherDataFromDb()

        return view
    }


    /*
    Permet de selectionner les informations concernant le mode Programmé pour les réutiliser lors du mode programmé
     */
    override fun onSelection(valeurP: ValeurProgramme?) {
        val programmeOrder = ProgrammeOrder(valeurP!!.acceleration!!.toInt(), valeurP.speed!!.toInt(),
                valeurP.direction!!, valeurP.tableSteps!!.toInt(), valeurP.frame!!.toInt(), valeurP.camera_number!!.toInt(),
                valeurP.timeBetweenPhotosNumber!!.toInt(), valeurP.focusStacking!!)
        ListOrder.list.add(programmeOrder)
        Menu.orderAdapter!!.notifyDataSetChanged()
        if (valeurP.camera_number!!.toInt() != 0) {
            var dataInit = "0,7,1"
            for (i in 0 until valeurP.camera_number!!.toInt()) {
                dataInit += ",1"
            }
            for (i in valeurP.camera_number!!.toInt()..8) {
                dataInit += ",0"
            }
            for (i in 0..8) {
                dataInit += ",0"
            }
            println("dataInit")
            peripherique!!.envoyer(dataInit)

        }

        var data = ""
        data += programmeOrder.id.toString() + ","
        data += "0" + ","
        data += valeurP.acceleration + ","
        data += valeurP.speed + ","
        data += valeurP.tableSteps + ","
        data += if (valeurP.direction == true) {
            "1" + ","
        } else {
            "0" + ","
        }
        data += "-1" + "," //choix rotation
        data += "-1" + "," //rotation number
        data += valeurP.frame + ","
        data += valeurP.camera_number + ","
        data += valeurP.timeBetweenPhotosNumber + ","
        println(valeurP.focusStacking == true)
        data += if (valeurP.focusStacking == true) {
            (FocusParametre.cameraAdapter!!.nombrePhotoFocus + 1).toString()
        } else {
            "0"
        }
        println(data)
        peripherique!!.envoyer(data)
        changeListener.onChangeFragment(Menu.menu)
        //Programme.focus_stackingSwitch!!.isChecked = false
    }

    /*
    Est appelé lors du click sur le bouton delete present sur le fragment et permet ainsi de revenir sur le mode programmé
     */
    override fun onDelete(valeurP: ValeurProgramme) {
        val task = kotlinx.coroutines.Runnable {
            valeurReelAndProgDataBase?.vPDao()?.delete(valeurP)
        }
        mDbThread.postTask(task)
        //fragmentManager!!.beginTransaction().replace(R.id.fragment, Programme.programme).addToBackStack(null).commit()
        changeListener.onChangeFragment(Menu.menu)
    }

    companion object {
        var bddProgramme = BddProgramme()
    }

    private fun fetchWeatherDataFromDb() {
        val task = kotlinx.coroutines.Runnable {
            val valeurProgrammeData = valeurReelAndProgDataBase?.vPDao()?.getAll()
            mUiHandler.post {
                if (valeurProgrammeData == null || valeurProgrammeData.isEmpty()) {
                    Toast.makeText(MainActivity.context!!, "Rien Dans la BDD", Toast.LENGTH_SHORT).show()
                    changeListener.onChangeFragment(Menu.menu)
                } else {
                    adapter = ValeurProgrammeAdapter(valeurProgrammeData)
                    mListView.adapter = adapter
                    adapter!!.setmListener(this)
                    mListView.onItemClickListener = OnItemClickListener { _, _, position, _ -> Toast.makeText(context, position, Toast.LENGTH_SHORT).show() }
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
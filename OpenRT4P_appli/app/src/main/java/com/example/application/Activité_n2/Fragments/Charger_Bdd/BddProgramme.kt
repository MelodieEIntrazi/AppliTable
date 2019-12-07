package com.example.application.Activité_n2.Fragments.Charger_Bdd

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import android.widget.Toast
import com.example.application.Activité_n2.Adapter.ValeurProgrammeAdapter
import com.example.application.Activité_n2.Fragments.Programmé.Programme
import com.example.application.Activité_n2.Interface.SelectionProgramme
import com.example.application.Activité_n2.MainActivity
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
        val transaction = fragmentManager!!.beginTransaction()
        val fragment = Programme()
        val bundle = Bundle()
        bundle.putString("vitesse", valeurP!!.speed)
        bundle.putString("acceleration", valeurP.acceleration)
        bundle.putString("tempsEntrePhotos", valeurP.timeBetweenPhotosNumber)
        bundle.putString("frame", valeurP.frame)
        bundle.putString("camera", valeurP.camera_number)
        bundle.putBoolean("direction", valeurP.direction!!)
        bundle.putBoolean("focus", valeurP.focusStacking!!)
        bundle.putString("tableSteps", valeurP.tableSteps)
        fragment.arguments = bundle
        transaction.replace(R.id.fragment, fragment).addToBackStack(null).commit()
    }

    /*
    Est appelé lors du click sur le bouton delete present sur le fragment et permet ainsi de revenir sur le mode programmé
     */
    override fun onDelete(valeurP: ValeurProgramme) {
        val task = kotlinx.coroutines.Runnable {
            valeurReelAndProgDataBase?.vPDao()?.delete(valeurP)
        }
        mDbThread.postTask(task)
        fragmentManager!!.beginTransaction().replace(R.id.fragment, Programme.programme).addToBackStack(null).commit()
    }

    companion object {
        var bddProgramme = BddProgramme()
    }

    private fun fetchWeatherDataFromDb() {
        val task = kotlinx.coroutines.Runnable {
            val valeurProgrammeData = valeurReelAndProgDataBase?.vPDao()?.getAll()
            mUiHandler.post {
                if (valeurProgrammeData == null || valeurProgrammeData.size == 0) {
                    Toast.makeText(MainActivity.context!!, "Rien Dans la BDD", Toast.LENGTH_SHORT)
                    fragmentManager!!.beginTransaction().replace(R.id.fragment, Programme.programme).addToBackStack(null).commit()
                } else {
                    adapter = ValeurProgrammeAdapter(valeurProgrammeData)
                    mListView.adapter = adapter
                    adapter!!.setmListener(this)
                    mListView.onItemClickListener = OnItemClickListener { parent, view, position, id -> Toast.makeText(context, position, Toast.LENGTH_SHORT).show() }
                }
            }
        }
        mDbThread.postTask(task)
    }
}
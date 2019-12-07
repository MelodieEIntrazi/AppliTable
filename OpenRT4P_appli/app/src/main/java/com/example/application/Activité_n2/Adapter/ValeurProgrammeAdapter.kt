package com.example.application.Activité_n2.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.application.Activité_n2.Fragments.Charger_Bdd.BddProgramme
import com.example.application.Activité_n2.Interface.SelectionProgramme
import com.example.application.Activité_n2.MainActivity
import com.example.application.Objets.ValeurProgramme
import com.example.application.R

class ValeurProgrammeAdapter(var mListeVP: List<ValeurProgramme>?) : BaseAdapter(), View.OnClickListener {
    private val mInflater: LayoutInflater
    private var mListener: SelectionProgramme? = null
    fun setmListener(mListener: SelectionProgramme?) {
        this.mListener = mListener
    }

    override fun getCount(): Int {
        return if (null != mListeVP) mListeVP!!.size else 0
    }

    override fun getItem(position: Int): Any {
        return if (null != mListeVP) mListeVP!![position] else Any()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun getViewUglyWay(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = mInflater.inflate(R.layout.valeur_programme_adapter, null)
        val valeurP = getItem(position) as ValeurProgramme
        val id = view.findViewById<TextView>(R.id.idProgramme)
        id.text = valeurP.id
        val speed = view.findViewById<TextView>(R.id.speedProgramme)
        speed.text = valeurP.speed
        val acceleration = view.findViewById<TextView>(R.id.accelerationProgramme)
        acceleration.text = valeurP.acceleration
        val frame = view.findViewById<TextView>(R.id.frameProgramme)
        frame.text = valeurP.frame
        val camera = view.findViewById<TextView>(R.id.cameraProgramme)
        camera.text = valeurP.camera_number
        val steps = view.findViewById<TextView>(R.id.stepsProgramme)
        steps.text = valeurP.tableSteps
        val timeBetweenPhotos = view.findViewById<TextView>(R.id.timeBetweenphotosProgramme)
        timeBetweenPhotos.text = valeurP.timeBetweenPhotosNumber
        val direction = view.findViewById<Switch>(R.id.directionProgramme)
        val focus = view.findViewById<Switch>(R.id.focusStackingChoix)
        direction.isChecked = valeurP.direction!!
        direction.isClickable = false
        val selection = view.findViewById<Button>(R.id.okProgramme)
        val deleteButton = view.findViewById<Button>(R.id.deleteProgramme)
        return view
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getViewBestWay(position, convertView, parent)
    }

    private fun getViewBestWay(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: ViewHolderProgramme
        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.valeur_programme_adapter, null)
            holder = ViewHolderProgramme(convertView)
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolderProgramme
        }
        val valeurP = getItem(position) as ValeurProgramme
        holder.id.text = valeurP.id
        holder.acceleration.text = valeurP.acceleration
        holder.camera.text = valeurP.camera_number
        holder.frame.text = valeurP.frame
        holder.speed.text = valeurP.speed
        holder.steps.text = valeurP.tableSteps
        holder.timeBetweenPhotos.text = valeurP.timeBetweenPhotosNumber
        holder.direction.isChecked = valeurP.direction!!
        holder.direction.isClickable = false
        holder.focus.isChecked = valeurP.focusStacking!!
        holder.focus.isClickable = false
        holder.selection.tag = position
        holder.selection.setOnClickListener(this)
        holder.suppression.tag = position
        holder.suppression.setOnClickListener(this)
        return convertView!!
    }

    override fun onClick(v: View) {
        val position = v.tag as Int
        val valeurP = getItem(position) as ValeurProgramme
        val idAPasser: String? = null
        when (v.id) {
            R.id.deleteProgramme -> {
                Toast.makeText(MainActivity.context, "Suppression", Toast.LENGTH_LONG).show()
                if (null != mListener) {

                    mListener!!.onDelete(valeurP)
                }
            }
            R.id.okProgramme -> {
                Toast.makeText(MainActivity.context, "Selection", Toast.LENGTH_LONG).show()
                if (null != mListener) {
                    mListener!!.onSelection(valeurP)
                }
            }
        }
    }

    init {
        mInflater = LayoutInflater.from(BddProgramme.bddProgramme.context)
    }
}
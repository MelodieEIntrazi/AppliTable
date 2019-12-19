package com.example.application.Activité_n2.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.application.Activité_n2.Fragments.Charger_Bdd.BddTempsReel
import com.example.application.Activité_n2.Interface.SelectionReel
import com.example.application.Activité_n2.MainActivity
import com.example.application.Objets.ValeurReel
import com.example.application.R

class ValeurReelAdapter(var mListeVR: List<ValeurReel>) : BaseAdapter(), View.OnClickListener {
    private val mInflater: LayoutInflater
    private var mListener: SelectionReel? = null
    //private val mBDDAsyncTask: SuppressionBDDR
    fun setmListener(mListener: SelectionReel?) {
        this.mListener = mListener
    }

    override fun getCount(): Int {
        return mListeVR.size
    }

    override fun getItem(position: Int): Any {
        return mListeVR[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun getViewUglyWay(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = mInflater.inflate(R.layout.valeur_reel_adapter, null)
        val valeurR = getItem(position) as ValeurReel
        val id = view.findViewById<TextView>(R.id.idReel)
        id.text = valeurR.id
        val speed = view.findViewById<TextView>(R.id.speedReel)
        speed.text = valeurR.speed
        val acceleration = view.findViewById<TextView>(R.id.accelerationReel)
        acceleration.text = valeurR.acceleration
        val nbRotation = view.findViewById<TextView>(R.id.rotationReel)
        nbRotation.text = valeurR.rotationNumber
        val steps = view.findViewById<TextView>(R.id.stepsReel)
        steps.text = valeurR.tableSteps
        val direction = view.findViewById<Switch>(R.id.directionReel)
        direction.isChecked = valeurR.direction!!
        direction.isClickable = false
        val choixRotation = view.findViewById<Switch>(R.id.choixRotationReel)
        direction.isChecked = valeurR.rotationMode!!
        direction.isClickable = false
        val selection = view.findViewById<Button>(R.id.okReel)
        val deleteButton = view.findViewById<Button>(R.id.deleteReel)
        return view
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getViewBestWay(position, convertView, parent)
    }

    private fun getViewBestWay(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: ViewHolderReel
        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.valeur_reel_adapter, null)
            holder = ViewHolderReel(convertView)
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolderReel
        }
        val valeurR = getItem(position) as ValeurReel
        holder.id.text = valeurR.id
        holder.acceleration.text = valeurR.acceleration
        holder.nbRotation.text = valeurR.rotationNumber
        holder.speed.text = valeurR.speed
        holder.steps.text = valeurR.tableSteps
        holder.direction.isChecked = valeurR.direction!!
        holder.direction.isClickable = false
        holder.rotationMode.isChecked = valeurR.rotationMode!!
        holder.rotationMode.isClickable = false
        holder.selection.tag = position
        holder.selection.setOnClickListener(this)
        holder.suppression.tag = position
        holder.suppression.setOnClickListener(this)
        return convertView!!
    }

    override fun onClick(v: View) {
        val position = v.tag as Int
        val valeurR = getItem(position) as ValeurReel
        when (v.id) {
            R.id.deleteReel -> {
                Toast.makeText(MainActivity.context, "Suppression", Toast.LENGTH_LONG).show()
                if (null != mListener) {
                    mListener!!.onDelete(valeurR)
                }
            }
            R.id.okReel -> {
                Toast.makeText(MainActivity.context, "Selection", Toast.LENGTH_LONG).show()
                if (null != mListener) {
                    mListener!!.onSelection(valeurR)
                }
            }
        }
    }

    init {
        mInflater = LayoutInflater.from(BddTempsReel.bddTempsReel.context)
    }
}
package com.example.application.Activité_n2.Interface

import com.example.application.Objets.ValeurReel

interface SelectionReel {
    fun onSelection(valeurR: ValeurReel?)
    fun onDelete(valeurR: ValeurReel)
}
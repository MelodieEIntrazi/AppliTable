package com.example.application.Activité_n2.Interface

import com.example.application.Objets.ValeurProgramme

interface SelectionProgramme {
    fun onSelection(valeurP: ValeurProgramme?)
    fun onDelete(valeurP: ValeurProgramme)
}
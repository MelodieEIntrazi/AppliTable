package com.example.application.Activité_n2.Interface

import com.example.application.Objets.ValeurProgramme

//Utiliser dans la bdd Programme pour selectionner ou supprimer un programme
interface SelectionProgramme {
    fun onSelection(valeurP: ValeurProgramme?)
    fun onDelete(valeurP: ValeurProgramme)
}
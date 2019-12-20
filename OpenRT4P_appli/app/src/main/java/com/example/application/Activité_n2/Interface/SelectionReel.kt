package com.example.application.Activité_n2.Interface

import com.example.application.Objets.ValeurReel

//Utiliser dans la bdd Programme pour selectionner ou supprimer un programme reel
interface SelectionReel {
    fun onSelection(valeurR: ValeurReel?)
    fun onDelete(valeurR: ValeurReel)
}
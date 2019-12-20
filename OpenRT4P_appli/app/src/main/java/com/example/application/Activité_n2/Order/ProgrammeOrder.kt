package com.example.application.Activité_n2.Order

class ProgrammeOrder(private val acceleration: Int, private val vitesse: Int, private val direction: Boolean, private val nombre_de_pas_table: Int, val nombre_de_prise: Int, val nombre_de_camera: Int,
                     private val temps_pause_entre_photos: Int, val focus_stacking: Boolean, var progression: Int = 1) : Order() {

    override fun createDatagramme(): String? {
        return ""
    }
}
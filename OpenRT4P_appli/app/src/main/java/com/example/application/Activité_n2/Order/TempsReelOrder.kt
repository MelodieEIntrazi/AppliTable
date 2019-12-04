package com.example.application.Activité_n2.Order

class TempsReelOrder(val acceleration: Int, val vitesse: Int, val isDirection: Boolean, val steps: Int, val isTimeMode: Boolean, val rotation_number: Int) : Order() {

    override fun createDatagramme(): String? {
        return ""
    }

}
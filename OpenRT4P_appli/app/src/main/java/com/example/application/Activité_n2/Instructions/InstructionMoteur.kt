package com.example.application.Activité_n2.Instructions
class InstructionMoteur(override var idCommande: Int, override var idInstruction: Int, val acceleration: Int, val vitesse: Int, val direction: Int, val choixRotation: Int, val stepsTime: Int) : Instruction() {
    override fun createDatagramme(): String? {
        return null
    }

}
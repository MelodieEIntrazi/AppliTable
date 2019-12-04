package com.example.application.Activité_n2.Instructions

class InstructionCamera(override var idCommande: Int, override var idInstruction: Int, val frame: Int, val pause: Int) : Instruction() {
    override fun createDatagramme(): String? {
        return null
    }

}
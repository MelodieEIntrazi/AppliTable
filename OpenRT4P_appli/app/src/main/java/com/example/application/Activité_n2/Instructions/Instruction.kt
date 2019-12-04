package com.example.application.Activité_n2.Instructions

abstract class Instruction internal constructor() {
    open var idCommande = 0
        protected set
    open var idInstruction = 0
        protected set
    @JvmField
    var termine = 0
    val type: String
        get() = this.javaClass.simpleName

    abstract fun createDatagramme(): String?

    companion object {
        private const val lastid = 0
    }
}
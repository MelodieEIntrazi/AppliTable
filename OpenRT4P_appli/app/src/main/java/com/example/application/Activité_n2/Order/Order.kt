package com.example.application.Activité_n2.Order

import com.example.application.Activité_n2.Instructions.Instruction
import java.util.*

/*Cette classe permet de lier Programme Order et TempsReelOrder */
abstract class Order internal constructor() {
    val id: Int
    @JvmField
    var listInstruction = ArrayList<Instruction>()
    val type: String
        get() = this.javaClass.simpleName

    abstract fun createDatagramme(): String?

    companion object {
        private var lastid = 0
    }

    init {
        id = ++lastid
    }
}
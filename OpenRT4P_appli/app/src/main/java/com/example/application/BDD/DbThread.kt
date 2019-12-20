package com.example.application.BDD

import android.os.Handler
import android.os.HandlerThread

//Permet de manipuler la bdd sans utiliser le main thread
class DbThread(threadName: String) : HandlerThread(threadName) {

    private var mHandler: Handler? = null

    override fun onLooperPrepared() {
        super.onLooperPrepared()
        mHandler = Handler(this.looper)
    }

    fun postTask(task: Runnable) {
        if (mHandler == null) {
            onLooperPrepared()
        }
        mHandler!!.post(task)
    }
}
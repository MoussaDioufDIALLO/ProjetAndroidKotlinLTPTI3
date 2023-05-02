package com.kiptechie.composenotes

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NoteApp : Application() {
    //Initiation de l'application
    init {
        instance = this
    }

    companion object {

        //Instance de l'application

        private var instance: NoteApp? = null

        //Récupération du contexte de l'application

        val context: Context
            get() = instance!!.applicationContext
    }
}

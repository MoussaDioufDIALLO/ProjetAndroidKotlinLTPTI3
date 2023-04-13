package com.kiptechie.composenotes

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NoteApp : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: NoteApp? = null

        val context: Context
            get() = instance!!.applicationContext
    }
}

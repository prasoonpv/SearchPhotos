package com.prasoon.task

import android.content.Context
import com.prasoon.task.data.core.dagger.components.DaggerTestAppMainComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class MainApplication : DaggerApplication() {

    private lateinit var instance: MainApplication

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    @Synchronized
    fun getInstance(): MainApplication {
        return instance
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerTestAppMainComponent.builder().create(this)
    }
}

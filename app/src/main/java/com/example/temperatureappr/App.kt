package com.example.temperatureappr

import android.app.Application
import com.example.temperatureappr.di.module.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        // Adding Koin modules to our application
        startKoin {
            androidContext(this@App)
            modules(appModules)
        }
    }
}
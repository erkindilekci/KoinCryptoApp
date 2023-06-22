package com.erkindilekci.koincryptoapp

import android.app.Application
import com.erkindilekci.koincryptoapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KoinCryptoApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@KoinCryptoApp)
            modules(appModule)
        }
    }
}

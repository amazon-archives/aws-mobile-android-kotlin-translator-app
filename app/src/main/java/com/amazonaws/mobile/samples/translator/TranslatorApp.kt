package com.amazonaws.mobile.samples.translator

import android.app.Application
import com.amazonaws.mobile.samples.translator.services.TranslateService
import com.amazonaws.mobile.samples.translator.services.mock.MockTranslateService
import org.koin.android.ext.android.startKoin
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

class TranslatorApp : Application() {
    companion object {
        private val servicesModule : Module = applicationContext {
            bean { MockTranslateService() as TranslateService }
        }
    }
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(servicesModule))
    }
}
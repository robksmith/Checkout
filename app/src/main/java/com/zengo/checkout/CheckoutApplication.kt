package com.zengo.checkout

import android.app.Application
import com.zengo.checkout.models.dagger2.components.ApplicationComponent
import com.zengo.checkout.models.dagger2.components.DaggerApplicationComponent
import com.zengo.checkout.models.dagger2.modules.ApplicationModule
import com.zengo.checkout.models.dagger2.modules.NetworkModule

class CheckoutApplication : Application()
{
    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerApplicationComponent.builder()
            .networkModule(NetworkModule(this))
            .applicationModule(ApplicationModule(this))
            .build()
        appComponent.injection(this)
    }
}
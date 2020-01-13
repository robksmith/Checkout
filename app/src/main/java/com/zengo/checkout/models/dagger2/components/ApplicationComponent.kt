package com.zengo.checkout.models.dagger2.components

import com.zengo.checkout.CheckoutApplication
import com.zengo.checkout.viewmodels.CheckoutViewModel
import com.zengo.checkout.models.dagger2.modules.ApplicationModule
import com.zengo.checkout.models.dagger2.modules.NetworkModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [ApplicationModule::class, NetworkModule::class])
interface ApplicationComponent {

    fun injection(target: CheckoutApplication)
    fun injection(target: CheckoutViewModel)
}

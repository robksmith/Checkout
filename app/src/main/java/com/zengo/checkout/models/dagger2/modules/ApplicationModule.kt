package com.zengo.checkout.models.dagger2.modules

import com.zengo.checkout.CheckoutApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/*
    ApplicationModule provides an Application with various singletons
*/

@Module
class ApplicationModule(private val app: CheckoutApplication)
{
    @Provides
    @Singleton
    fun provideApp(): CheckoutApplication
    {
        return app
    }
}
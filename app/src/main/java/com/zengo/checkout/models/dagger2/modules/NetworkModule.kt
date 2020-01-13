package com.zengo.checkout.models.dagger2.modules

import android.app.Application
import com.zengo.checkout.CheckoutApplication
import com.zengo.checkout.helpers.BuildTypesHelper
import com.zengo.checkout.networking.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class NetworkModule(private val app: CheckoutApplication) {

    private var ws : ApiService? = null

    @Provides
    @Singleton
    internal fun provideRetrofit(): ApiService
    {
        return getCheckoutApiService(app)!!
    }

    private fun getCheckoutApiService(application: Application): ApiService?
    {
        if ( ws == null)
        {
            val okHttpClientBuilder = OkHttpClient.Builder()

            okHttpClientBuilder.connectTimeout(30, TimeUnit.SECONDS)
            okHttpClientBuilder.readTimeout(30, TimeUnit.SECONDS)
            okHttpClientBuilder.writeTimeout(30, TimeUnit.SECONDS)

            if (BuildTypesHelper.isDebug())
            {
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY

                okHttpClientBuilder.addInterceptor(logging)
            }

            val okClient = okHttpClientBuilder.
                    build()

            ws = Retrofit.Builder().
                addCallAdapterFactory(RxJava2CallAdapterFactory.create()).
                addConverterFactory(GsonConverterFactory.create()).
                    client((okClient)).
                baseUrl(CHECKOUT_BASE_URL).
                build().
                create(ApiService::class.java)
        }

        return ws
    }

    companion object {

        val CHECKOUT_BASE_URL = "https://integrations-cko.herokuapp.com/"
    }
}
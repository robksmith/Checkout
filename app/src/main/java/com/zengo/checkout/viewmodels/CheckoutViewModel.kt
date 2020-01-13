package com.zengo.checkout.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.zengo.checkout.CheckoutApplication
import com.zengo.checkout.models.api.CardPaymentRequest
import com.zengo.checkout.models.api.CardPaymentResponse
import com.zengo.checkout.models.livedata.ILiveDataResult
import com.zengo.checkout.models.livedata.LiveDataResultModel
import com.zengo.checkout.networking.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

open class CheckoutViewModel constructor(app: Application) : AndroidViewModel(app)
{
    var cardDetails: CardPaymentRequest = CardPaymentRequest()

    // card payment response - live
    var postCardPaymentLive: MutableLiveData<LiveDataResultModel<ILiveDataResult>?>? =
        MutableLiveData()
        private set

    @Inject
    lateinit var api: ApiService

    init
    {
        // field injection
        (app as CheckoutApplication).appComponent.injection(this)
    }

    fun callPostCardPayment()
    {
        postCardPaymentLive?.value = LiveDataResultModel.loading(null, 0)

        val resultObs = api.cardPaymentPOST(body = cardDetails)
        val disposable =
            resultObs.subscribeOn(Schedulers.newThread())?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ model -> successCard(model) }, { error -> failureCard(error) })
    }

    private fun successCard(model: CardPaymentResponse)
    {
        postCardPaymentLive?.value = LiveDataResultModel.success(model, 0)
    }

    private fun failureCard(error: Throwable)
    {
        postCardPaymentLive?.value = LiveDataResultModel.error("", null, 0)
    }
}

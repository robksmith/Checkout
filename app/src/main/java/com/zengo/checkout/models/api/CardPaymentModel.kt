package com.zengo.checkout.models.api

import com.google.gson.annotations.SerializedName
import com.zengo.checkout.models.livedata.ILiveDataResult

class CardPaymentRequest(val number: String, val expiry_month: String, val expiry_year: String, val cvv: String)
{
    constructor() : this("4243754271700719", "06", "2030", "100")

    val success_url: String
    val failure_url: String

    init
    {
        this.success_url = "https://checkout.com"
        this.failure_url = "https://www.bbc.co.uk"
    }
}

class CardPaymentResponse: ILiveDataResult {

    @SerializedName("url")
    var url: String? = null
}
package com.zengo.checkout.networking

import com.zengo.checkout.models.api.CardPaymentRequest
import com.zengo.checkout.models.api.CardPaymentResponse
import io.reactivex.Observable
import retrofit2.http.*
import java.util.HashMap

interface ApiService
{
    @POST("pay")
    fun cardPaymentPOST(@HeaderMap headers: Map<String, String> = buildApiHeaders(), @Body body: CardPaymentRequest): Observable<CardPaymentResponse>

    companion object ServiceGenerator
    {
        private fun buildApiHeaders(): HashMap<String, String>
        {
            val map = HashMap<String, String>()

            map["Content-Type"] = "application/json"
            map["Accept"] = "application/json"

            return map
        }
    }
}

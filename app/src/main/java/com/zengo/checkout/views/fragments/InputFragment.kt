package com.zengo.checkout.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.jakewharton.rxbinding.widget.RxTextView
import com.zengo.checkout.R
import com.zengo.checkout.models.api.CardPaymentRequest
import com.zengo.checkout.viewmodels.CheckoutViewModel
import kotlinx.android.synthetic.main.input_fragment.*
import rx.Observable
import rx.Subscription

class InputFragment : BaseFragment()
{
    private lateinit var vm: CheckoutViewModel
    private var subscription1: Subscription? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        vm = ViewModelProviders.of(activity!!).get(CheckoutViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view1 = inflater.inflate(R.layout.input_fragment, container, false)
        return view1
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        button_pay?.setOnClickListener {

            // grab the card details
            vm.cardDetails = CardPaymentRequest(cardNumberEditText.cardNumber, expiryDateEditText.month, expiryDateEditText.year, cvvEditText.text.toString())

            // go to call payment fragment
            val action = InputFragmentDirections.actionInputFragmentToCallCardPaymentFragment()
            findNavController().navigate(action)
        }
    }

    override fun onStart()
    {
        super.onStart()

        val cardObservable: Observable<CharSequence> = RxTextView.textChanges(cardNumberEditText)
        val dateEntry: Observable<CharSequence> = RxTextView.textChanges(expiryDateEditText)

        val combined: Observable<Boolean> = Observable.combineLatest(cardObservable, dateEntry) { card, date ->

            // NOTE - validation needs improving - also - there is no validation on CVV
            val cardValid = cardNumberEditText.isValid
            val dateValid = expiryDateEditText.isValid
            val cvvValid = true                         // Note - no validation on cvv

            val allGood = cardValid && dateValid && cvvValid

            allGood
        }

        // and subscribe to the combined
        subscription1 = combined.subscribe { okToEnablePay -> button_pay.isEnabled = okToEnablePay }
    }

    override fun onStop()
    {
        super.onStop()

        subscription1?.unsubscribe()
    }
}

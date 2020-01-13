package com.zengo.checkout.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.zengo.checkout.R
import com.zengo.checkout.models.api.CardPaymentResponse
import com.zengo.checkout.models.livedata.ILiveDataResult
import com.zengo.checkout.models.livedata.LiveDataResultModel
import com.zengo.checkout.viewmodels.CheckoutViewModel
import com.zengo.checkout.models.livedata.LiveDataResultStatus
import kotlinx.android.synthetic.main.call_payment_request_fragment.*

class CallCardPaymentFragment : BaseFragment()
{
    private lateinit var vm: CheckoutViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        vm = ViewModelProviders.of(activity!!).get(CheckoutViewModel::class.java)

        vm.postCardPaymentLive?.observe(this, Observer(::observeCardPayment))

        // make the call
        vm.callPostCardPayment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.call_payment_request_fragment, container, false)
        return view
    }

    private fun observeCardPayment(liveData: LiveDataResultModel<ILiveDataResult>?)
    {
        when (liveData?.status)
        {
            LiveDataResultStatus.LOADING ->
            {
                call_payment_progress.visibility = View.VISIBLE
            }

            LiveDataResultStatus.SUCCESS ->
            {
                call_payment_progress.visibility = View.GONE

                val response = liveData.data
                if ( response is CardPaymentResponse)
                {
                    // NOTE: would normally check response here and take action
                    if (false)//response.hasErrors())
                    {
                        // show error
                        findNavController().popBackStack()
                    }
                    else
                    {
                        // we don't want to pop up to here - pop up to goes back to the input fragment
                        val options = NavOptions.Builder().setPopUpTo(R.id.inputFragment, false).build()

                        // Success - go to Secure 3D fragment
                        findNavController().navigate(CallCardPaymentFragmentDirections.actionCallCardPaymentFragmentToSecure3DFragment(response.url), options)
                    }
                }
            }

            LiveDataResultStatus.ERROR ->
            {
                call_payment_progress.visibility = View.GONE

                // NOTE: Would need to deal with this in a more suitable fashion - but for this demo, show a toast
                Toast.makeText(activity, "Bad format - please re-enter", Toast.LENGTH_LONG).show()

                // and then just go back
                findNavController().popBackStack()
            }
        }
    }
}

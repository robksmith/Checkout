package com.zengo.checkout.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.zengo.checkout.R
import kotlinx.android.synthetic.main.fragment_result.*

class ResultFragment : Fragment()
{
    private val args: ResultFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        if (args.success)
        {
            result_textView.text = "Payment Completed"
            result_imageView.setImageDrawable(ContextCompat.getDrawable(context!!,R.drawable.vector_tick))
        }
        else
        {
            result_textView.text = "Payment Failed"
            result_imageView.setImageDrawable(ContextCompat.getDrawable(context!!, R.drawable.vector_cross))
        }
    }
}

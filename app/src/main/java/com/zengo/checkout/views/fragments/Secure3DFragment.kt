package com.zengo.checkout.views.fragments

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.zengo.checkout.R
import com.zengo.checkout.viewmodels.CheckoutViewModel
import kotlinx.android.synthetic.main.secure_3d_fragment.*

interface IProgress
{
    fun showProgress()
    fun hideProgress()
}

class Secure3DFragment : BaseFragment(), IProgress
{
    private lateinit var vm: CheckoutViewModel

    private val args: Secure3DFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        vm = ViewModelProviders.of(activity!!).get(CheckoutViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.secure_3d_fragment, container, false)

        if ( args.url != null )
        {
            val webViewClient = Secure3DWebViewClient(context!!, this, this)

            val webview = view.findViewById<WebView>(R.id.secure_3d_webview)
            webview.webViewClient = webViewClient

            webview.settings.allowContentAccess = true
            webview.settings.javaScriptEnabled = true
            webview.settings.domStorageEnabled = true
            webview.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
            webview.loadUrl(args.url)
        }

        return view
    }

    override fun showProgress()
    {
        secure_3d_progress?.visibility = View.VISIBLE
    }

    override fun hideProgress()
    {
        secure_3d_progress?.visibility = View.INVISIBLE
    }
}


class Secure3DWebViewClient(val context: Context, private val frag: Secure3DFragment, private val progress: IProgress) : WebViewClient()
{
    private var isFinished: Boolean = false

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?)
    {
        super.onPageStarted(view, url, favicon)

        // show spinner
        progress.showProgress()

        // act on url
        if ( url != null && !isFinished)
        {
            when
            {
                url.contains("bbc.co.uk") ->
                {
                    proceedToResult(false)
                    return
                }

                url.contains("checkout.com") ->
                {
                    proceedToResult(true)
                    return
                }
            }
        }
    }

    override fun onPageFinished(view: WebView, url: String)
    {
        // hide progress
        progress.hideProgress()
    }

    /*
        To make the webview load the links and not open another browser
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean
    {
        view?.loadUrl(request?.url.toString())
        return true
    }

    /*
        To make the webview load the links and not open another browser
     */
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean
    {
        view?.loadUrl(url)
        return true
    }

    private fun proceedToResult(success:Boolean)
    {
        terminate()

        // we don't want to pop up to here - pop up to goes back to the input fragment
        val options = NavOptions.Builder().setPopUpTo(R.id.inputFragment, false).build()

        // Go to result
        frag.findNavController().navigate(Secure3DFragmentDirections.actionSecure3DFragmentToResultFragment(success), options)
    }

    private fun terminate()
    {
        isFinished = true
    }
}

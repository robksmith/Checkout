package com.zengo.checkout.views.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText

import android.text.*
import android.view.inputmethod.EditorInfo
import com.zengo.checkout.helpers.ExpiryDateInputFilter
import com.zengo.checkout.helpers.RegExPattern.ValidExpiryDate

class ExpiryEntry : EditText
{
    private var isFormatting: Boolean = false

    val isValid: Boolean
        get()
        {
            return text.matches(ValidExpiryDate)
        }

    val month: String
        get() = text.toString().substring(0,2)

    val year: String
        get() = text.toString().substring(3,7)

    constructor(context: Context) : super(context)
    {
        setup()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    {
        setup()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)
    {
        setup()
    }

    private fun setup()
    {
        // The input filters
        filters = arrayOf(ExpiryDateInputFilter(), InputFilter.LengthFilter(7) )
        imeOptions = ( EditorInfo.IME_ACTION_DONE or EditorInfo.IME_FLAG_NO_EXTRACT_UI )

        addTextChangedListener(object : TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int)
            {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {
                Selection.setSelection(text, start+count)
            }

            private fun formatExpiryDateAsRequired(existingText: String): String
            {
                var new = ""
                var containsSlash = false

                if(existingText.contains("/") ) containsSlash=true

                for (index in existingText.indices)
                {
                    val atTheEnd = (existingText.length-1==index)

                    new += existingText[index]
                    if ( index==1 && !containsSlash && !atTheEnd)
                        new += "/"
                }
                return new
            }

            override fun afterTextChanged(editable: Editable)
            {
                if ( isFormatting ) return

                val correct = editable.matches(ValidExpiryDate)
                if ( !correct )
                {
                    isFormatting = true
                    setText(formatExpiryDateAsRequired(editable.toString()))
                }

                isFormatting = false
            }
        })
    }
}

package com.zengo.checkout.views.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText

import android.text.*
import android.view.inputmethod.EditorInfo
import com.zengo.checkout.helpers.RegExPattern
import com.zengo.checkout.helpers.CreditCardInputFilter


class CardEntry : EditText
{
    private var isFormatting: Boolean = false

    // invalid characters (non-digits)
    private val regexNonDigit = "[^\\d]".toRegex()

    // return the current digits only of the card
    val cardNumber: String
        get() = text.toString().replace(regexNonDigit, "")

    // For the sake of this demo, a valid card is one which is 16 digits in length
    // ** but would really have to find out formats of accepted cards and check that instead
    val isValid: Boolean
        get()
        {
            return cardNumber.length == 16

            /*

            should really do something like this...

            return when
            {
                cardNumber.matches(RegExPattern.Visa.toRegex()) -> true
                cardNumber.matches(RegExPattern.Mastercard.toRegex()) -> true
                else -> false
            }

            */
        }

    // what logoId id do we require for the current card number??
    private val logoId: Int
    get()
    {
        return when
        {
            cardNumber.matches(RegExPattern.Visa.toRegex()) -> 1
            cardNumber.matches(RegExPattern.Mastercard.toRegex()) -> 2

            else -> 0
        }
    }

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
        // Changing the icon when it's empty
        showCurrentCardLogo()

        // The input filters
        filters = arrayOf(CreditCardInputFilter(), InputFilter.LengthFilter(19) )
        imeOptions = ( EditorInfo.IME_ACTION_DONE or EditorInfo.IME_FLAG_NO_EXTRACT_UI )

        // Adding the TextWatcher
        addTextChangedListener(object : TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int)
            {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {
                if ( isFormatting ) return
                isFormatting = true

                // if the string doesn't match our required format
                if (true)
                {
                    // now format it into our specific format - eg 1111-2222-3333-4444
                    val cardWithDashes = formatCreditCardAsRequired(cardNumber)

                    // set text on the edittext
                    setText(cardWithDashes)
                    setTextKeepState(cardWithDashes)

                    // Put cursor at the end
                    Selection.setSelection(text, cardWithDashes.length)
                }

                isFormatting = false
            }

            override fun afterTextChanged(editable: Editable)
            {
                showCurrentCardLogo()
            }

            // add a dash after every 4th digit - would need something more complex than this
            private fun formatCreditCardAsRequired(cc: String): String
            {
                var new = ""

                for (index in cc.indices)
                {
                    val atTheEnd = (cc.length-1==index)

                    new += cc[index]
                    if ( ( index==3 || index ==7 || index ==11 ) && !atTheEnd )
                        new += "-"
                }
                return new
            }
        })
    }

    // For example - set a drawable / update other image with logo of detected card
    private fun showCurrentCardLogo()
    {
        /*
        when ( logoId )
        {
            0 -> setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.none, 0)
            1 -> setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.visa, 0)
            2 -> setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.mastercard, 0)
        }
        */
    }
}

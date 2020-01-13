package com.zengo.checkout.helpers

import android.text.*
import java.util.regex.Pattern

open class RegexInputFilter : InputFilter
{
    private val patternValid: Pattern

    constructor(pattern: String) : this(Pattern.compile(pattern))
    constructor(pattern: Pattern?)
    {
        if (pattern == null)
        {
            throw IllegalArgumentException("Requires a regex.")
        }

        patternValid = pattern
    }

    override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int, dend: Int): CharSequence?
    {
        val matcher = patternValid.matcher(source)
        return when
        {
            !matcher.matches() -> "" // reject
            else -> null // accept
        }
    }
}

class CreditCardInputFilter : RegexInputFilter(SPECIAL_CHARACTER_REGEX)
{
    companion object
    {
        private const val SPECIAL_CHARACTER_REGEX = "^[0-9-]*\$"
    }
}

class ExpiryDateInputFilter : RegexInputFilter(SPECIAL_CHARACTER_REGEX)
{
    companion object
    {
        private const val SPECIAL_CHARACTER_REGEX = "^[0-9//]*\$"
    }
}

object RegExPattern
{
    const val Visa = "^4[0-9]{12}(?:[0-9]{3})?$"
    const val Mastercard = "...???...etc etc"

    val ValidExpiryDate = "^\\d{2}/\\d{4}\$".toRegex()
}

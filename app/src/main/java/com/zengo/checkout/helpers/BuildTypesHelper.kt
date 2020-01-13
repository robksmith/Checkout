package com.zengo.checkout.helpers

import com.zengo.checkout.BuildConfig

object BuildTypesHelper
{
    fun isDebug() : Boolean
    {
        return BuildConfig.DEBUG
    }
}
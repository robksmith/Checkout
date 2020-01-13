package com.zengo.checkout.models.livedata

interface ILiveDataResult{}

enum class LiveDataResultStatus
{
    LOADING, SUCCESS, ERROR
}

data class LiveDataResultModel<out T>(val status: LiveDataResultStatus, val data: T?, val message: String?, val code:Int)
{
    companion object
    {
        fun <T> loading(data: T?, code:Int): LiveDataResultModel<T>
        {
            return LiveDataResultModel(LiveDataResultStatus.LOADING, data, null, code)
        }

        fun <T> success(data: T?, code:Int): LiveDataResultModel<T>
        {
            return LiveDataResultModel(LiveDataResultStatus.SUCCESS, data, null, code)
        }

        fun <T> error(msg: String, data: T?, code:Int): LiveDataResultModel<T>
        {
            return LiveDataResultModel(LiveDataResultStatus.ERROR, data, msg, code)
        }
    }
}
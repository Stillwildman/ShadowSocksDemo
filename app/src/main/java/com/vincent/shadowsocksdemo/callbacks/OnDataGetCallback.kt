package com.vincent.shadowsocksdemo.callbacks

/**
 * Created by Vincent on 2020/2/4.
 */
interface OnDataGetCallback<Data> {

    fun onDataGet(data: Data?)

    fun onDataGetFailed(errorMessage: String?)

}
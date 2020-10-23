package com.vincent.shadowsocksdemo.network

import com.vincent.shadowsocksdemo.callbacks.OnDataGetCallback
import com.vincent.shadowsocksdemo.model.ApiUrls
import com.vincent.shadowsocksdemo.model.Const
import com.vincent.shadowsocksdemo.model.items.EventMessage
import com.vincent.shadowsocksdemo.utilities.LogUtil
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.internal.EverythingIsNonNull

/**
 * Created by Vincent on 2020/2/4.
 */
object DataCallbacks : ApiUrls {

    private const val TAG = "DataCallbacks"

    private fun getApiInterface(): ApiInterface? {
        return WebAgent.getRetrofit().create(ApiInterface::class.java)
    }

    private fun <Data> enqueue(call: Call<Data>, dataGet: OnDataGetCallback<Data>) {
        LogUtil.i(TAG, "Call URL: " + call.request().url.toString())

        call.enqueue(object : Callback<Data> {
            @EverythingIsNonNull
            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                LogUtil.d(TAG, "Call onResponse!!! \nMessage: ${response.message()} IsSuccessful: ${response.isSuccessful}")

                sendNetworkDoneEvent()

                if (response.isSuccessful) {
                    dataGet.onDataGet(response.body())
                }
                else {
                    dataGet.onDataGetFailed(response.message())
                }
            }

            @EverythingIsNonNull
            override fun onFailure(call: Call<Data>, t: Throwable) {
                LogUtil.e(TAG, "Call onFailure!!!\n${t.message}")
                sendNetworkDoneEvent()
                dataGet.onDataGetFailed(t.message)
            }
        })
    }

    private fun sendNetworkDoneEvent() {
        EventBus.getDefault().post(EventMessage(Const.EVENT_NETWORK_DONE))
    }
}
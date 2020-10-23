package com.vincent.shadowsocksdemo.network

import com.vincent.shadowsocksdemo.model.ApiUrls
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.ref.SoftReference

/**
 * Create and setting the Retrofit objects and client.
 *
 * @author Vincent Chang (2020/02/04)
 */
object WebAgent {

    private var retrofitRef: SoftReference<Retrofit?>? = null

    fun getRetrofit(): Retrofit {
        if (retrofitRef == null || retrofitRef?.get() == null) {
            retrofitRef = SoftReference(newRetrofit(ApiUrls.BASE_URL))
        }
        return retrofitRef!!.get()!!
    }

    fun getRetrofit(baseUrl : String): Retrofit {
        retrofitRef = SoftReference(newRetrofit(baseUrl))
        return retrofitRef!!.get()!!
    }

    private fun newRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }

}
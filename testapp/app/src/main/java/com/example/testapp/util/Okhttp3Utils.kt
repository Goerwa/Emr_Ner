package com.example.testapp

import okhttp3.*

class Okhttp3Utils {
    private val okHttpClient: OkHttpClient
    init {
//        okHttpClient = OkHttpClient.Builder().addInterceptor(object : Interceptor{
//            override fun intercept(chain: Interceptor.Chain): Response {
//                val originalRequest = chain.request().newBuilder().header("Authorization","APPCODE 5cb04855819641e4bbb124bb0b1a4723").build()
//                return chain.proceed(originalRequest)
//            }
//        }).build()
        okHttpClient = OkHttpClient()
    }

    companion object {
        private var okHttp3Utils: Okhttp3Utils? = null
        val instance: Okhttp3Utils
            get() {
                if (okHttp3Utils == null)
                    okHttp3Utils = Okhttp3Utils()
                return okHttp3Utils as Okhttp3Utils
            }
    }

    //----------------------------------------------------------------------------------------------------------------------------------
    fun getNews(url: String, callback: Callback) {
        val request = Request.Builder().url(url).header("Authorization","APPCODE 5cb04855819641e4bbb124bb0b1a4723").build()
        okHttpClient.newCall(request).enqueue(callback)
    }
    fun get(url:String,callback:Callback){
        val request = Request.Builder().url(url).build()
        okHttpClient.newCall(request).enqueue(callback)
    }
    fun post(url: String, body: FormBody,callback: Callback) {
        val request = Request.Builder().post(body).url(url).build()
        okHttpClient.newCall(request).enqueue(callback)

    }
}

package com.example.taskapp.utils


//class RetrofitClient {
//    private val retrofit: Retrofit
//
//    init {
//
//        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
//            .callTimeout(99999, TimeUnit.SECONDS)
//            .connectTimeout(99999, TimeUnit.SECONDS)
//            .readTimeout(99999, TimeUnit.SECONDS)
//            .writeTimeout(99999, TimeUnit.SECONDS)
//        retrofit = Retrofit.Builder()
//            .baseUrl(Constants.URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(httpClient.build())
//            .build()
//    }
//
//    val apiFeed: FeedInterface
//        get() = retrofit.create(FeedInterface::class.java)
//
//    val apiSignin: LoginInterface
//        get() = retrofit.create(LoginInterface::class.java)
//
//    val apiSignup: CadastroInterface
//        get() = retrofit.create(CadastroInterface::class.java)

//    companion object {
//        private var mInstance: RetrofitClient? = null
//
//        @get:Synchronized
//        val instance: RetrofitClient?
//            get() {
//                if (mInstance == null) mInstance = RetrofitClient()
//                return mInstance
//            }
//    }
//}

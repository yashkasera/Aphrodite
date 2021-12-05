package com.yashkasera.aphrodite.service

import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import com.yashkasera.aphrodite.BuildConfig
import com.yashkasera.aphrodite.model.User
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

/**
 * @author yashkasera
 * Created 05/12/21 at 12:01 AM
 */
interface RetrofitService {

    @POST("$DIR/user/signIn")
    suspend fun signIn(@Body user: User): Response<User>

    @PATCH("$DIR/user")
    suspend fun updateUser(
        @Header("Authorization") authToken: String,
        @Body user: User
    ): Response<Any>

    @PATCH("$DIR/user/fcm")
    suspend fun updateFcm(
        @Header("Authorization") authToken: String,
        @Query("token") token: String
    ): Response<Any>

    @POST("$DIR/user/signUp")
    suspend fun signUp(@Body user: User): Response<User>

    @POST("$DIR/sos")
    suspend fun sos(
        @Header("Authorization") authToken: String
    ): Response<Any>

    companion object {
        // TODO: 05/12/21 Change BASE_URL
        private const val BASE_URL = "https://5f0b-45-118-157-135.ngrok.io/"
        private const val DIR = "api/v1"
        fun create(): RetrofitService {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val builder = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) {
                builder.addInterceptor(OkHttpProfilerInterceptor())
            }
            val client = builder.build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetrofitService::class.java)
        }
    }
}
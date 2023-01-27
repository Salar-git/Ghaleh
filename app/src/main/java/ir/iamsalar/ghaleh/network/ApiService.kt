package ir.iamsalar.ghaleh.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import ir.iamsalar.ghaleh.network.model.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

//Base URL
private val BASE_URL = "http://138.201.58.73:3030/api/"
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ApiService {

    @POST("login")
    suspend fun login(@Body logInModel: LogInModel): Response<LogInResponseModel>


    @GET("categories")
    suspend fun getCategories(@Header("Authorization") authorization: String): Response<List<String>>

    @POST("content/filter")
    suspend fun getContent(
        @Header("Authorization") authorization: String,
        @Body contentFilterModel: ContetnFilterModel
    ): Response<List<ContentResponseModel>>


    @POST("content/{id}/like")
    suspend fun like(
        @Header("Authorization") authorization: String,
        @Path("id") id: Int
    ): Response<LikeResponseModel>


}

object Api {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
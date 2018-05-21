package com.tnurdinov.yflickr

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

interface FlickrApiService {

    @GET("services/rest")
    fun recent(@Query("method") method: String = "flickr.photos.getRecent",
               @Query("api_key") apiKey: String = "f191c6e6a88ce8f213dd24f932d000c5",
               @Query("per_page") perPage: Int = 20,
               @Query("page") page: Int = 1,
               @Query("format") format: String = "json",
               @Query("nojsoncallback") noJsonCallback: Int = 1): Call<PhotosResponse>

    @GET("services/rest")
    fun search(@Query("method") method: String = "flickr.photos.search",
               @Query("api_key") apiKey: String = "f191c6e6a88ce8f213dd24f932d000c5",
               @Query("text") text: String,
               @Query("per_page") perPage: Int = 20,
               @Query("page") page: Int = 1,
               @Query("safe_search") searchSafe: Int = 1,
               @Query("media") media: Int = 1,
               @Query("accuracy") accuracy: Int = 1,
               @Query("in_gallery") ig: Int =1,
               @Query("format") format: String = "json",
               @Query("nojsoncallback") noJsonCallback: Int = 1): Call<PhotosResponse>

    companion object Factory {
        fun create(): FlickrApiService {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build()

            val retrofit = Retrofit.Builder()
                    .addConverterFactory(MoshiConverterFactory.create())
                    .baseUrl("https://api.flickr.com/")
                    .client(client)
                    .build()

            return retrofit.create(FlickrApiService::class.java)
        }
    }
}
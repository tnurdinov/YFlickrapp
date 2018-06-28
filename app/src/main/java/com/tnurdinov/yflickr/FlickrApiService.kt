package com.tnurdinov.yflickr

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

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
}
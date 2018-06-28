package com.tnurdinov.yflickr.repository

import com.tnurdinov.yflickr.FlickrApiService
import com.tnurdinov.yflickr.PhotosResponse
import retrofit2.Call
import javax.inject.Inject


class FlickrRepository @Inject constructor(val flickrApiService: FlickrApiService) {

    fun getRercent() : Call<PhotosResponse> {
        return flickrApiService.recent()
    }
}
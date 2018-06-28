package com.tnurdinov.yflickr.viewmodel

import android.arch.lifecycle.ViewModel
import com.tnurdinov.yflickr.PhotosResponse
import com.tnurdinov.yflickr.repository.FlickrRepository
import retrofit2.Call
import javax.inject.Inject

class FlickrViewModel @Inject constructor(private val flickrRepository: FlickrRepository) : ViewModel() {

    fun loadRecent() : Call<PhotosResponse> {
        return flickrRepository.getRercent()
    }
}
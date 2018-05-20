package com.tnurdinov.yflickr

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FlickrApiService.Factory.create().request("flickr.photos.getRecent").enqueue(object: Callback<GetResent> {
            override fun onResponse(call: Call<GetResent>?, response: Response<GetResent>?) {
                response?.body()?.photos?.photo?.forEach { photo -> Log.d("TAG", "https://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}_m.jpg") }
            }

            override fun onFailure(call: Call<GetResent>?, t: Throwable?) {
                Log.d("TAG", t?.localizedMessage)
            }
        })
    }
}

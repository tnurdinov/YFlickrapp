package com.tnurdinov.yflickr

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var searchView: SearchView
    private lateinit var myDataset: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myDataset = ArrayList()

        handleIntent(intent)

        viewManager = LinearLayoutManager(this)
        viewAdapter = MyAdapter(myDataset)

        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
            setHasFixedSize(true)

            layoutManager = viewManager

            adapter = viewAdapter

        }

        FlickrApiService.Factory.create().recent().enqueue(object: Callback<PhotosResponse> {
            override fun onResponse(call: Call<PhotosResponse>?, response: Response<PhotosResponse>?) {
                myDataset.clear()
                response?.body()?.photos?.photo?.forEach { photo -> myDataset.add("https://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}_m.jpg") }
                viewAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<PhotosResponse>?, t: Throwable?) {
                Log.d("TAG", t?.localizedMessage)
            }
        })
    }

    override fun onNewIntent(intent: Intent) {
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        val searchQuery = intent.getStringExtra(SearchManager.QUERY)
        if (Intent.ACTION_SEARCH == intent.action) {

            val suggestions = SearchRecentSuggestions(baseContext,
                    RecentSuggestionProvider.AUTHORITY, RecentSuggestionProvider.MODE)
            suggestions.saveRecentQuery(searchQuery, null)

            Log.d("TAG search query ==>", searchQuery)
            FlickrApiService.Factory.create().search(text = searchQuery).enqueue(object: Callback<PhotosResponse> {
                override fun onResponse(call: Call<PhotosResponse>?, response: Response<PhotosResponse>?) {
                    myDataset.clear()
                    response?.body()?.photos?.photo?.forEach { photo -> myDataset.add("https://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}_m.jpg") }
                    viewAdapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<PhotosResponse>?, t: Throwable?) {
                    Log.d("TAG", t?.localizedMessage)
                }
            })

        } else if (Intent.ACTION_VIEW == intent.action) {
            Log.d("TAG view query ==>", searchQuery)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.search, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.action_search).actionView as SearchView

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(componentName))
        searchView.setIconifiedByDefault(false)
        return super.onCreateOptionsMenu(menu)
    }
}

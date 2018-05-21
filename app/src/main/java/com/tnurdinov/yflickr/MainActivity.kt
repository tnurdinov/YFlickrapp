package com.tnurdinov.yflickr

import android.content.SharedPreferences
import android.database.MatrixCursor
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
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
    private lateinit var matrixCursor: MatrixCursor
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myDataset = ArrayList()

        val columns = arrayOf("_id", "recent")
        matrixCursor = MatrixCursor(columns)

        sharedPref = this.getSharedPreferences("name", MODE_PRIVATE)

        viewManager = LinearLayoutManager(this)
        viewAdapter = MyAdapter(myDataset)

        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.search, menu)
        searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                myDataset.clear()
                FlickrApiService.Factory.create().search(text = p0.toString()).enqueue(object: Callback<PhotosResponse> {
                    override fun onResponse(call: Call<PhotosResponse>?, response: Response<PhotosResponse>?) {
                        response?.body()?.photos?.photo?.forEach { photo -> myDataset.add("https://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}_m.jpg") }
                        viewAdapter.notifyDataSetChanged()
                    }

                    override fun onFailure(call: Call<PhotosResponse>?, t: Throwable?) {
                        Log.d("TAG", t?.localizedMessage)
                    }
                })
                updateRecentSuggestions(p0)

                searchView.isIconified = true
                searchView.clearFocus();
                return false
            }

        })
        searchView.suggestionsAdapter = RecentsAdapter(this, matrixCursor, searchView)
        return super.onCreateOptionsMenu(menu)
    }

    private fun updateRecentSuggestions(p0: String?) {
        matrixCursor.addRow(arrayOf(1, "dog"))
        matrixCursor.addRow(arrayOf(2, "cat"))
    }
}

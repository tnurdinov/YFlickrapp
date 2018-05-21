package com.tnurdinov.yflickr

import android.content.Context
import android.database.Cursor
import android.support.v4.widget.CursorAdapter
import android.support.v7.widget.SearchView
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.Toast
import android.widget.TextView


class RecentsAdapter : CursorAdapter {
    private var mLayoutInflater: LayoutInflater
    private var searchView: SearchView

    constructor(context: Context, cursor: Cursor, searchView: SearchView) : super(context, cursor, false) {
        this.searchView = searchView
        mLayoutInflater = LayoutInflater.from(context)
    }

    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
        return mLayoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false)
    }

    override fun bindView(view: View, context: Context?, cursor: Cursor?) {
        val deal = cursor?.getString(cursor.getColumnIndexOrThrow("recent"))

        val dealsTv = view.findViewById(android.R.id.text1) as TextView
        dealsTv.text = deal

        view.setOnClickListener(View.OnClickListener { view ->
            searchView.isIconified = true
            Toast.makeText(context, "Selected suggestion " + 12,
                    Toast.LENGTH_LONG).show()
        })

    }
}
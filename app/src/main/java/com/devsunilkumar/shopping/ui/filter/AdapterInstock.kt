package com.devsunilkumar.shopping.ui.filter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.devsunilkumar.shopping.R

class AdapterInstock(private val context: Activity, private val itmes: Array<String>)
: ArrayAdapter<String>(context, R.layout.in_stock_list, itmes) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.in_stock_list, null, true)

        val titleText = rowView.findViewById(R.id.textView) as TextView

        titleText.text = itmes[position]

        return rowView
    }
}
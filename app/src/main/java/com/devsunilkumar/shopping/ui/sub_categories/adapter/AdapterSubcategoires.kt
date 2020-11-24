package com.devsunilkumar.shopping.ui.sub_categories.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.devsunilkumar.shopping.R
import com.devsunilkumar.shopping.base.RecyclerViewClickListener
import com.devsunilkumar.shopping.databinding.ListSubcatgoriesItemsBinding
import com.devsunilkumar.shopping.model.sub_categories_products.SubcategoriesProducts
import com.devsunilkumar.shopping.utils.RecyclerViewHolder


class AdapterSubcategoires(
    private val result: List<SubcategoriesProducts>,
    private val listener: RecyclerViewClickListener<SubcategoriesProducts>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RecyclerViewHolder(
        DataBindingUtil.inflate<ListSubcatgoriesItemsBinding>(
            LayoutInflater.from(parent.context),
            R.layout.list_subcatgories_items,
            parent,
            false
        )
    )

    override fun getItemCount() = result.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding: ListSubcatgoriesItemsBinding =
            (holder as RecyclerViewHolder<*>).binding as ListSubcatgoriesItemsBinding

        binding.viewModel = result[position]
        binding.executePendingBindings()

       binding.cvListMain.setOnClickListener {
            listener.onRecyclerViewItemClick(it,result[position])
        }



    }



}
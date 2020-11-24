package com.devsunilkumar.shopping.ui.categories.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.devsunilkumar.shopping.R
import com.devsunilkumar.shopping.base.RecyclerViewClickListener
import com.devsunilkumar.shopping.databinding.ListCategoriesItemsBinding
import com.devsunilkumar.shopping.model.categories.ProductCategories
import com.devsunilkumar.shopping.utils.RecyclerViewHolder


class Adaptercategories(
    private val result: List<ProductCategories>,
    private val listener: RecyclerViewClickListener<ProductCategories>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RecyclerViewHolder(
        DataBindingUtil.inflate<ListCategoriesItemsBinding>(
            LayoutInflater.from(parent.context),
            R.layout.list_categories_items,
            parent,
            false
        )
    )

    override fun getItemCount() = result.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding: ListCategoriesItemsBinding =
            (holder as RecyclerViewHolder<*>).binding as ListCategoriesItemsBinding

        binding.viewModel = result[position]
        binding.executePendingBindings()


        binding.mainLayout.setOnClickListener {
            listener.onRecyclerViewItemClick(it,result[position])

        }





    }



}
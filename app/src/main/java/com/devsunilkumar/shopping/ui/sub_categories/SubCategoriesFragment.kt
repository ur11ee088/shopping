package com.devsunilkumar.shopping.ui.sub_categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.devsunilkumar.shopping.R
import com.devsunilkumar.shopping.base.BaseFragment
import com.devsunilkumar.shopping.base.RecyclerViewClickListener
import com.devsunilkumar.shopping.databinding.FragmentSubcategoriesBinding
import com.devsunilkumar.shopping.factory.HomeViewModelFactory
import com.devsunilkumar.shopping.model.sub_categories_products.SubcategoriesProducts
import com.devsunilkumar.shopping.viewmodel.HomeViewModel
import com.devsunilkumar.shopping.ui.filter.FilterDialogFragment
import com.devsunilkumar.shopping.ui.sub_categories.adapter.AdapterSubcategoires
import com.devsunilkumar.shopping.utils.isInternetAvailable
import com.devsunilkumar.shopping.utils.noInternetPopup
import kotlinx.coroutines.launch
import org.kodein.di.generic.instance

class SubCategoriesFragment : BaseFragment(), RecyclerViewClickListener<SubcategoriesProducts> {

    private lateinit var viewModel: HomeViewModel
    private val factory: HomeViewModelFactory by instance()
    private lateinit var binding: FragmentSubcategoriesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProvider(this, factory).get(HomeViewModel::class.java)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_subcategories, container, false)
        binding.lifecycleOwner = this
        setViewModel(viewModel)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (isInternetAvailable(requireContext())) {
            getsubcategoriesLists()
            viewModel._msubcategoriesProducts.observe(viewLifecycleOwner, Observer {
                binding.mprogress.visibility = View.GONE
                bindUi(it.arrayOfProducts)

            })


        } else {
            noInternetPopup(requireContext())
        }


    }

    fun getsubcategoriesLists() = launch {
        binding.mprogress.visibility = View.VISIBLE
        viewModel.getSubCategories()
    }

    fun bindUi(list: List<SubcategoriesProducts>) {

        binding.rvsubCategories.also {
            it.layoutManager = LinearLayoutManager(context)
            it.setHasFixedSize(true)
            it.adapter = AdapterSubcategoires(list, this)

        }
    }

    override fun onRecyclerViewItemClick(view: View, item: SubcategoriesProducts) {

        FilterDialogFragment().show(parentFragmentManager, "")

    }


}
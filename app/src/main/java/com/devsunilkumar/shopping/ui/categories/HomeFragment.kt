package com.devsunilkumar.shopping.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.devsunilkumar.shopping.R
import com.devsunilkumar.shopping.base.BaseFragment
import com.devsunilkumar.shopping.base.RecyclerViewClickListener
import com.devsunilkumar.shopping.databinding.FragmentHomeBinding
import com.devsunilkumar.shopping.factory.HomeViewModelFactory
import com.devsunilkumar.shopping.model.categories.ProductCategories
import com.devsunilkumar.shopping.ui.categories.adapter.Adaptercategories
import com.devsunilkumar.shopping.utils.isInternetAvailable
import com.devsunilkumar.shopping.utils.noInternetPopup
import kotlinx.coroutines.launch
import org.kodein.di.generic.instance

class HomeFragment : BaseFragment(), RecyclerViewClickListener<ProductCategories> {

    private lateinit var viewModel: HomeViewModel
    private val factory: HomeViewModelFactory by instance()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
        setViewModel(viewModel)
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (isInternetAvailable(requireContext())) {
            getcategoriesLists()

            viewModel._mCategoryResponse.observe(viewLifecycleOwner, Observer {
                 binding.homeMainLayout.visibility = View.VISIBLE
                if (it.arrayOfProducts != null)

                    bindUi(it.arrayOfProducts)
            })

        } else {
            noInternetPopup(requireContext())

        }
    }

    fun getcategoriesLists() = launch {
        viewModel.getCategoriesList()
    }

    fun bindUi(list: List<ProductCategories>) {

        binding.rvCategories.also {
            it.layoutManager = GridLayoutManager(context, 2)
            it.setHasFixedSize(true)
            it.adapter = Adaptercategories(list, this)

        }

    }

    override fun onRecyclerViewItemClick(view: View, item: ProductCategories) {

        val bundle = bundleOf("_mycategories" to item)
        findNavController().navigate(R.id.nav_subcategories_fragment, bundle)

    }
}
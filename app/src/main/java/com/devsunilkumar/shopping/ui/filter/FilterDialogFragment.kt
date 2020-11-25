package com.devsunilkumar.shopping.ui.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.algolia.instantsearch.core.connection.ConnectionHandler
import com.algolia.instantsearch.core.number.range.Range
import com.algolia.instantsearch.helper.filter.range.FilterRangeConnector
import com.algolia.instantsearch.helper.filter.range.connectView
import com.algolia.instantsearch.helper.filter.state.FilterGroupID
import com.algolia.instantsearch.helper.filter.state.FilterState
import com.algolia.instantsearch.helper.filter.state.filters
import com.algolia.search.model.Attribute
import com.devsunilkumar.shopping.R
import com.devsunilkumar.shopping.databinding.FilterlayoutBinding
import com.devsunilkumar.shopping.factory.HomeViewModelFactory
import com.devsunilkumar.shopping.viewmodel.HomeViewModel
import com.devsunilkumar.shopping.utils.snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import kotlin.coroutines.CoroutineContext


class FilterDialogFragment() : DialogFragment(), CoroutineScope,
    KodeinAware {

    private lateinit var binding: FilterlayoutBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var job: Job
    override val kodein by kodein()
    private val factory: HomeViewModelFactory by instance()
    val language = arrayOf<String>(

        "java",
        "android",
        "Kotlin",
        "Python",
        "Java Script",
        "flutter",
        "react native"
    )
    private val price = Attribute("price")
    private val groupID = FilterGroupID(price)
    private val primaryBounds = 100..20000
    private val initialRange = 100..20000
    private val filters = filters {
        group(groupID) {
            range(price, initialRange)
        }
    }
    private val filterState = FilterState(filters)
    private val range =
        FilterRangeConnector(filterState, price, range = initialRange, bounds = primaryBounds)
    private val connection = ConnectionHandler(
        range
    )


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.filterlayout, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        job = Job()


        binding.close.setOnClickListener {
            dialog?.dismiss()

        }
        binding.apply.setOnClickListener {
            dialog?.dismiss()
            view?.snackbar("apply changes done")
        }

        binding.clear.setOnClickListener {
            view?.snackbar("Clear all changes")

            dialog?.dismiss()
        }

        connection += range.connectView(RangeSliderView(binding.slider))
        connection += range.connectView(RangeTextView(binding.rangeLabel))
        connection += range.connectView(BoundsTextView(binding.boundsLabel))


        binding.buttonResetBounds.setOnClickListener {
            range.viewModel.bounds.value = Range(primaryBounds)
            it.isEnabled = false
            binding.buttonChangeBounds.isEnabled = true
        }


        val myListAdapter = AdapterInstock(requireActivity(), language)
        binding.listview.adapter = myListAdapter


    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
}




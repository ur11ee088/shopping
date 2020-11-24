package com.devsunilkumar.shopping.base

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.devsunilkumar.shopping.utils.snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.json.JSONObject
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import kotlin.coroutines.CoroutineContext


abstract class BaseFragment : Fragment(), CoroutineScope, KodeinAware {

    override val kodein by kodein()
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }

    protected fun setViewModel(viewModel: BaseViewModel) {
        viewModel.error.observe(viewLifecycleOwner, Observer {
            when (it.code) {
                401 -> {
                    showErrorMessage(it.message)
                }
                400 -> {
                    showErrorMessage(it.message)
                }
                else -> {
                    showErrorMessage(it.message)
                }
            }
        })

    }

    private fun showErrorMessage(message: String) {
        try {
            val errorObject = JSONObject(message)
            if (errorObject.has("errors")) {
                val errors = errorObject.getJSONArray("errors")
                if (errors.length() > 0) {
                    val error = errors.getJSONObject(0)
                    view?.snackbar(error.getString("message"))
                }
            } else if (errorObject.has("message")) {
                view?.snackbar(errorObject.getString("message"))
            } else {
                view?.snackbar(message)
            }
        } catch (e: Error) {
            view?.snackbar(e.message.toString())

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
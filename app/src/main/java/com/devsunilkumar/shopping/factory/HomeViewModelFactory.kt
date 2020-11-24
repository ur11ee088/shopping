package com.devsunilkumar.shopping.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devsunilkumar.shopping.repository.HomeRepository
import com.devsunilkumar.shopping.ui.categories.HomeViewModel


@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(private val repository: HomeRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(repository) as T
    }
}
package com.devsunilkumar.shopping.ui.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devsunilkumar.shopping.base.BaseViewModel
import com.devsunilkumar.shopping.base.ErrorWrapper
import com.devsunilkumar.shopping.model.categories.CategoryResponse
import com.devsunilkumar.shopping.model.sub_categories_products.SubCategoriesResponse
import com.devsunilkumar.shopping.networkcalls.ApiException
import com.devsunilkumar.shopping.networkcalls.NoInternetException
import com.devsunilkumar.shopping.repository.HomeRepository

class HomeViewModel(private val repository: HomeRepository) : BaseViewModel() {
    private val _CategoryResponse = MutableLiveData<CategoryResponse>()
    val _mCategoryResponse: LiveData<CategoryResponse>
       get() = _CategoryResponse

    suspend fun getCategoriesList() {
        try {
            _CategoryResponse.value = repository.getCategoriesList()
        } catch (e: ApiException) {
            mError.value = ErrorWrapper(e.errorCode, e._message)
        } catch (e: NoInternetException) {
            mError.value = ErrorWrapper(e.errorCode, e._message)
        }
    }


    private val _SubcategoriesProducts = MutableLiveData<SubCategoriesResponse>()
    val _msubcategoriesProducts: LiveData<SubCategoriesResponse>
        get() = _SubcategoriesProducts

    suspend fun getSubCategories() {
        try {
            _SubcategoriesProducts.value = repository.getlistOfSubcategoires()
        } catch (e: ApiException) {
            mError.value = ErrorWrapper(e.errorCode, e._message)
        } catch (e: NoInternetException) {
            mError.value = ErrorWrapper(e.errorCode, e._message)
        }
    }




}
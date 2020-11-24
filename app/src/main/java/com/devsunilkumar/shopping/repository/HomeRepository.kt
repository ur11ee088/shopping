package com.devsunilkumar.shopping.repository

import com.devsunilkumar.shopping.networkcalls.MyShopApiCallsInterface
import com.devsunilkumar.shopping.networkcalls.SafeApiRequest


class HomeRepository(private var api: MyShopApiCallsInterface) : SafeApiRequest() {
    suspend fun getCategoriesList() = apiRequest { api.getListOfCategories() }
    suspend fun getlistOfSubcategoires() = apiRequest { api.getlistOfSubcategoires() }

}
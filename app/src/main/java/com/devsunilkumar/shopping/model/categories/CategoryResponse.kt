package com.devsunilkumar.shopping.model.categories

import java.io.Serializable

data class CategoryResponse(
    val arrayOfProducts: List<ProductCategories>?
):Serializable
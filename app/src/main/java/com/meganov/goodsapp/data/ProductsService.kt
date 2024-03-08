package com.meganov.goodsapp.data

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductsService {
    @GET("/products")
    fun getProducts(@Query("skip") skip: Int, @Query("limit") limit: Int): Single<ProductsResponse>
}
package com.meganov.goodsapp.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.meganov.goodsapp.data.Product
import com.meganov.goodsapp.data.ProductsService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class ProductListVM(private val api: ProductsService) : ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    private var page = 0

    init {
        loadProducts()
    }

    private val loadingErrTAG = "Page Loading Error"

    @SuppressLint("CheckResult")
    fun loadProducts() {
        api.getProducts(page * 20, 20)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ newProducts ->
                _products.value = _products.value.orEmpty() + newProducts.products
                page++
            }, { error ->
                Log.d(loadingErrTAG, "loadProducts: ${error.message}")
            })
    }
}

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
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading
    val products: LiveData<List<Product>> get() = _products

    private var page = 0

    init {
        loadProducts()
    }

    private val loadingErrTAG = "Page Loading Error"

    @SuppressLint("CheckResult")
    fun loadProducts() {
        _isLoading.value = true
        api.getProducts(page * 20, 20)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { _isLoading.value = false }
            .subscribe({ newProducts ->
                _products.value = _products.value.orEmpty() + newProducts.products
                page++
            }, { error ->
                Log.d(loadingErrTAG, "loadProducts: ${error.message}")
            })
    }

    @SuppressLint("CheckResult")
    fun searchProducts(query: String) {
        _products.value = emptyList()
        _isLoading.value = true
        _isLoading.value = false
        _isLoading.value = true
        api.search(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { _isLoading.value = false }
            .subscribe({ searchResults ->
                _products.value = searchResults.products
            }, { error ->
                Log.d(loadingErrTAG, "searchProducts: ${error.message}")
            })
    }

    fun getProductById(id: Int): Product? {
        if (id - 1 in _products.value!!.indices) {
            val productByIndex = _products.value!![id - 1]
            if (productByIndex.id == id - 1) {
                return productByIndex
            }
        }
        return _products.value!!.find { it.id == id }
    }
}

package com.meganov.goodsapp.ui.composables

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.meganov.goodsapp.ui.ProductListVM

@Composable
fun App(context: Context, viewModel: ProductListVM) {
    val products by viewModel.products.observeAsState(emptyList())
    ProductList(products = products, context = context)
}

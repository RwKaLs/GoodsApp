package com.meganov.goodsapp.ui.composables

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.meganov.goodsapp.ui.ProductListVM

@Composable
fun App(context: Context, viewModel: ProductListVM) {
    val products by viewModel.products.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "product_list") {
        composable("product_list") {
            ProductList(
                products = products,
                isLoading = isLoading,
                navController = navController,
                onLoadMore = viewModel::loadProducts
            )
        }
        composable("product_details/{product_id}") { navBackStackEntry ->
            val id: String? = navBackStackEntry.arguments?.getString("product_id")
            if (id == null) {
                Toast.makeText(context, "The product does not exist!", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.getProductById(id.toInt())?.let { ProductDetails(product = it) }
            }
        }
    }
}


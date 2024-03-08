package com.meganov.goodsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.meganov.goodsapp.data.ProductsService
import com.meganov.goodsapp.ui.ProductListVM
import com.meganov.goodsapp.ui.composables.App
import com.meganov.goodsapp.ui.theme.GoodsAppTheme
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoodsAppTheme {
                val viewModel = viewModel<ProductListVM>(
                    factory = object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            val api = Retrofit.Builder()
                                .baseUrl("https://dummyjson.com")
                                .addConverterFactory(GsonConverterFactory.create())
                                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                                .build()
                                .create(ProductsService::class.java)
                            return ProductListVM(api) as T
                        }
                    }
                )
                App(this, viewModel)
            }
        }
    }
}

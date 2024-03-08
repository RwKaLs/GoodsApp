package com.meganov.goodsapp.ui.composables

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.request.CachePolicy
import coil.transform.CircleCropTransformation
import com.meganov.goodsapp.R
import com.meganov.goodsapp.data.Product

@Composable
fun ProductList(products: List<Product>, context: Context) {
    LazyColumn {
        items(products) { product ->
            ProductItem(product, context)
        }
    }
}

@Composable
fun ProductItem(product: Product, context: Context) {
    Column {
        Text(product.title)
        Text(product.description)
        NetworkImage(
            url = product.thumbnail,
            modifier = Modifier
                .height(120.dp)
                .width(120.dp)
                .clip(RoundedCornerShape(8.dp))
        )
    }
}

@Composable
fun NetworkImage(url: String, modifier: Modifier = Modifier) {
    val painter = rememberImagePainter(
        data = url,
        builder = {
            crossfade(true)
            memoryCachePolicy(CachePolicy.ENABLED)
        }
    )
    Image(
        painter = painter,
        contentDescription = "ImagePainter",
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}

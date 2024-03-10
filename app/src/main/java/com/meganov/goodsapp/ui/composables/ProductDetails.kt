package com.meganov.goodsapp.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.request.CachePolicy
import com.meganov.goodsapp.R
import com.meganov.goodsapp.data.Product

@Composable
fun ProductDetails(product: Product) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            item {
                ImageCarousel(images = product.images)
                TextDetails(product = product)
            }
        }
    }
}

@Composable
fun ImageCarousel(images: List<String>) {
    val scrollState = rememberScrollState()
    Row(Modifier.horizontalScroll(scrollState)) {
        images.forEach { url ->
            CarouselItem(
                url = url,
                modifier = Modifier
                    .padding(5.dp)
                    .size(400.dp)
            )
        }
    }
}

@Composable
fun CarouselItem(url: String, modifier: Modifier = Modifier) {
    val painter = rememberImagePainter(
        data = url,
        builder = {
            memoryCachePolicy(CachePolicy.ENABLED)
            placeholder(R.color.transparent)
        }
    )
    Image(
        painter = painter,
        contentDescription = "Product Image",
        modifier = modifier
    )
}

@Composable
fun TextDetails(product: Product) {
    Text(
        text = product.title,
        style = MaterialTheme.typography.titleMedium,
        color = Color.White,
        fontWeight = FontWeight.Bold,
        fontSize = 35.sp
    )
    Spacer(modifier = Modifier.height(20.dp))
    RatingBar(rating = product.rating)
    Spacer(modifier = Modifier.height(20.dp))
    Row {
        Text(
            text = "${product.price} $",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Green,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )
        Spacer(modifier = Modifier.width(50.dp))
        Text(
            text = "-${product.discountPercentage}%",
            style = MaterialTheme.typography.titleMedium, color = Color.Red,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )
    }
    Spacer(modifier = Modifier.height(20.dp))
    Text(
        text = "Stock: ${product.stock}", style = MaterialTheme.typography.titleMedium,
        color = Color.White,
        fontSize = 20.sp
    )
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = "Brand: ${product.brand}", style = MaterialTheme.typography.titleMedium,
        color = Color.White,
        fontSize = 20.sp
    )
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = product.description,
        style = MaterialTheme.typography.bodyMedium,
        color = Color.White,
        fontSize = 20.sp,
        lineHeight = 24.sp
    )
}

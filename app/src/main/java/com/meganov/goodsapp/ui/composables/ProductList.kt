package com.meganov.goodsapp.ui.composables

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.request.CachePolicy
import com.meganov.goodsapp.R
import com.meganov.goodsapp.data.Product

@Composable
fun ProductList(
    products: List<Product>,
    isLoading: Boolean,
    navController: NavController,
    onSearch: (String) -> Unit,
    onLoadMore: () -> Unit
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val scrollState = rememberLazyListState()
    val closeToEnd =
        (scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) >= products.size - 2
    if (closeToEnd && !isLoading && searchQuery == "") {
        onLoadMore()
    }
    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.background).fillMaxSize()
    ) {
        TextField(
            value = searchQuery,
            onValueChange = { query ->
                searchQuery = query
                onSearch(query)
            },
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth()
        )

        LazyColumn(
            state = scrollState,
            contentPadding = PaddingValues(3.dp),
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            items(products) { product ->
                ProductItem(product, navController)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductItem(product: Product, navController: NavController) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(1.dp),
        colors = CardDefaults.cardColors(
            MaterialTheme.colorScheme.surface,
            MaterialTheme.colorScheme.onSurface
        ),
        onClick = {
            Log.d("PRODCHECK", "ProductItem: ${product.id} ${product.title}")
            navController.navigate("product_details/${product.id}")
        }
    ) {
        Column(
            modifier = Modifier.padding(5.dp)
        ) {
            Row {
                NetworkImage(
                    url = product.thumbnail,
                    modifier = Modifier
                        .height(170.dp)
                        .width(180.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.width(5.dp))
                DetailsCard(product = product)
            }
        }
    }
}

@Composable
fun NetworkImage(url: String, modifier: Modifier = Modifier) {
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
        modifier = modifier,
        contentScale = ContentScale.Crop,
    )
}

@Composable
fun DetailsCard(product: Product) {
    Column {
        Text(
            text = product.title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.White,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(5.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "${product.price} $",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Green
            )
            Spacer(modifier = Modifier.width(10.dp))
            if (product.discountPercentage > 0) {
                Text(
                    text = "(-${product.discountPercentage}%)",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        RatingBar(product.rating)
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = product.description,
            style = MaterialTheme.typography.bodySmall,
            color = Color.White,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun RatingBar(rating: Double, modifier: Modifier = Modifier) {
    Row {
        Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = "Star",
            tint = Color.Yellow
        )
        Text(
            text = String.format("%.1f", rating),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = modifier.padding(start = 4.dp)
        )
    }
}

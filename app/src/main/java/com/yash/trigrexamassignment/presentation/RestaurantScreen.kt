package com.yash.trigrexamassignment.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.yash.trigrexamassignment.R
import com.yash.trigrexamassignment.domain.models.Restaurant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantScreen(
    category: String,
    viewModel: RestaurantViewModel = hiltViewModel()
) {
    // Trigger category change event
    LaunchedEffect(category) {
        viewModel.onEvent(UiEvent.onCategoryChange(category))
    }

    val restaurants = viewModel.restaurants.collectAsLazyPagingItems()
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("1214 Holywood St.", style = MaterialTheme.typography.bodyLarge)
                }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search restaurants...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            items(restaurants.itemCount) { index ->
                val restaurant = restaurants[index]
                if (restaurant != null && restaurant.name.contains(searchQuery, ignoreCase = true)) {
                    RestaurantCard(restaurant)
                }
            }

            if (restaurants.loadState.refresh is LoadState.Loading) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            if (restaurants.loadState.append is LoadState.Loading) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            if (restaurants.loadState.refresh is LoadState.Error) {
                val e = restaurants.loadState.refresh as LoadState.Error
                item {
                    Text(
                        text = "Error: ${e.error.message}",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun RestaurantCard(restaurant: Restaurant) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = restaurant.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f))
                    .padding(12.dp)
            ) {
                Text(restaurant.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("Rating: ${restaurant.rating} ⭐")
                Text("${restaurant.price_level} · ${restaurant.delivery_time}")
            }
        }
    }
}


package com.yash.trigrexamassignment.presentation

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.yash.trigrexamassignment.R
import com.yash.trigrexamassignment.data.remote.FakeApiServer
import com.yash.trigrexamassignment.domain.models.Restaurant

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun RestaurantScreen(
    viewModel: RestaurantViewModel = hiltViewModel(),
) {
    // Trigger category change event

    var category by remember { mutableStateOf("All") }

    LaunchedEffect(category) {
        viewModel.onEvent(UiEvent.onCategoryChange(category))
    }

    val restaurants = viewModel.restaurants.collectAsLazyPagingItems()

    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                )
                Spacer(Modifier.height(20.dp))

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
            item {
                LazyRow(contentPadding = PaddingValues(10.dp)) {
                    item {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            GlideImage(
                                model = "https://media.istockphoto.com/id/545652444/vector/restaurant-cafe-menu-template-design.jpg?s=1024x1024&w=is&k=20&c=XR2tslsFB4Xj9BKRLvmJ7WBpx5QJnxT6D-YMpN75m1U=",
                                contentDescription = "All",
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(5.dp)
                                    .clip(CircleShape)
                                    .clickable{
                                        category = "All"
                                    },
                                contentScale = ContentScale.Crop
                            )
                            Text("All")
                        }
                    }

                    item {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            GlideImage(
                                model = "https://hips.hearstapps.com/hmg-prod/images/classic-cheese-pizza-recipe-2-64429a0cb408b.jpg?crop=0.8888888888888888xw:1xh;0,0&resize=1200:*",
                                contentDescription = "Pizza",
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(5.dp)
                                    .clip(CircleShape)
                                    .clickable{
                                        category = "Pizza"
                                    },
                                contentScale = ContentScale.Crop
                            )
                            Text("Pizza")
                        }
                    }

                    item {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            GlideImage(
                                model = "https://www.foodandwine.com/thmb/jldKZBYIoXJWXodRE9ut87K8Mag=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/crispy-comte-cheesburgers-FT-RECIPE0921-6166c6552b7148e8a8561f7765ddf20b.jpg",
                                contentDescription = "Burger",
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(5.dp)
                                    .clip(CircleShape)
                                    .clickable{
                                        category = "Burger"
                                    },
                                contentScale = ContentScale.Crop
                            )
                            Text("Burger")
                        }
                    }

                    item {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            GlideImage(
                                model = "https://media.istockphoto.com/id/459396345/photo/taco.jpg?s=1024x1024&w=is&k=20&c=ptqvfVJDsrNXzPUSUMVgbAkqIXotUf3a8lFAHBt9ZSE=",
                                contentDescription = "Taco",
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(5.dp)
                                    .clip(CircleShape)  .clickable{
                                        category = "Tacos"
                                    },
                                contentScale = ContentScale.Crop
                            )
                            Text("Taco")
                        }
                    }

                    item {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            GlideImage(
                                model = "https://plus.unsplash.com/premium_photo-1674601033631-79eeffaac6f9?q=80&w=928&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                contentDescription = "Chinese",
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(5.dp)
                                    .clip(CircleShape)
                                    .clickable{
                                        category = "Chinese"
                                    },
                                contentScale = ContentScale.Crop
                            )
                            Text("Chinese")
                        }
                    }

                    item {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            GlideImage(
                                model = "https://www.tasteofhome.com/wp-content/uploads/2019/05/Fried-Ice-Cream-Dessert-Bars-_EXPS_SDJJ19_232652_B02_06_1b_rms-2.jpg?fit=300,300&webp=1",
                                contentDescription = "Desserts",
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(5.dp)
                                    .clip(CircleShape)  .clickable{
                                        category = "Desserts"
                                    },
                                contentScale = ContentScale.Crop
                            )
                            Text("Desserts")
                        }
                    }

                    item {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            GlideImage(
                                model = "https://as2.ftcdn.net/v2/jpg/03/10/01/97/1000_F_310019753_1M07s7jaI3tQ3hubPWYLekZ6xx0PWVPN.jpg",
                                contentDescription = "Drinks",
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(5.dp)
                                    .clip(CircleShape).clickable{
                                        category = "Drinks"
                                    },
                                contentScale = ContentScale.Crop
                            )
                            Text("Drinks")
                        }
                    }
                }
            }


            items(restaurants.itemCount) { index ->
                val restaurant = restaurants[index]
                if (restaurant != null && restaurant.name.contains(
                        searchQuery,
                        ignoreCase = true
                    )
                ) {
                    RestaurantCard(restaurant)
                }
            }

            if (restaurants.loadState.refresh is LoadState.Loading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            if (restaurants.loadState.append is LoadState.Loading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
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

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RestaurantCard(restaurant: Restaurant) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            GlideImage(
                model = restaurant.image_url,
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




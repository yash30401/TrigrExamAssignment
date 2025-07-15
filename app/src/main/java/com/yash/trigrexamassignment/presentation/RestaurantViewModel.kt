package com.yash.trigrexamassignment.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import com.yash.trigrexamassignment.domain.models.Restaurant
import com.yash.trigrexamassignment.domain.use_case.GetRestaurantUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import org.junit.experimental.categories.Categories
import javax.inject.Inject

@HiltViewModel
class RestaurantViewModel @Inject constructor(
    private val getRestaurantsUseCase: GetRestaurantUseCase,
) : ViewModel() {

    private val _category = MutableStateFlow("All")
    private val _tab = MutableStateFlow("Recommended")

    val restaurants: Flow<PagingData<Restaurant>> =
        _category.combine(_tab) { category, tab ->
            Pair(category, tab)
        }
            .flatMapLatest { (category, tab) ->

                val minRatingFilter: Double? = if (tab == "Popular") {
                    4.3 // Apply filter for "Popular" tab
                } else {
                    null // No rating filter for other tabs (e.g., "Recommended")
                }
                if (minRatingFilter != null) {
                    getRestaurantsUseCase.invoke(category).map { it.filter { it.rating > 4.5 } }
                } else {
                    getRestaurantsUseCase.invoke(category)
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = PagingData.empty()
            )

    fun onEvent(event: UiEvent) {
        when (event) {
            is UiEvent.onCategoryChange -> {
                _category.value = event.category
            }

            is UiEvent.onTabChange -> _tab.value = event.tab
        }
    }
}
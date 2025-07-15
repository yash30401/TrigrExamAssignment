package com.yash.trigrexamassignment.presentation

sealed class UiEvent {

    data class onCategoryChange(val category: String) : UiEvent()
    data class onTabChange(val tab:String): UiEvent()
}
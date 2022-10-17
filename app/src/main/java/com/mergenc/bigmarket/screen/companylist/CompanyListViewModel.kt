package com.mergenc.bigmarket.screen.companylist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mergenc.bigmarket.domain.repository.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CompanyListViewModel @Inject constructor(
    private val repository: StockRepository
) : ViewModel() {
    val state by mutableStateOf(CompanyListState())

    fun onEvent(event: CompanyListEvent) {
        when (event) {
            is CompanyListEvent.Refresh -> {

            }
            is CompanyListEvent.OnSearchQueryChange -> {

            }
        }
    }
}
package com.mergenc.bigmarket.screen.companylist

import com.mergenc.bigmarket.domain.model.CompanyList

data class CompanyListState(
    val companies: List<CompanyList> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = "",
)
